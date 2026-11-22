"""Configuración común de la práctica: exchange topic, colas durables y cola de fallidos."""
import pika

EXCHANGE = "ssdc.eventos"
EXCHANGE_FALLIDOS = "ssdc.fallidos"
COLA_FALLIDOS = "q.fallidos"

# cola -> patrón de binding
COLAS = {
    "q.correo": "prepostulacion.*",            # aprobada y rechazada
    "q.notificaciones": "prepostulacion.aprobada",  # solo aprobadas
    "q.auditoria": "#",                        # todo evento
}


def conectar():
    credenciales = pika.PlainCredentials("demo", "demo-local")
    return pika.BlockingConnection(
        pika.ConnectionParameters("localhost", 5672, "/", credenciales)
    )


def preparar(canal):
    """Declara todo de forma idempotente: se puede llamar desde productor y consumidores."""
    canal.exchange_declare(EXCHANGE, exchange_type="topic", durable=True)
    canal.exchange_declare(EXCHANGE_FALLIDOS, exchange_type="fanout", durable=True)
    canal.queue_declare(COLA_FALLIDOS, durable=True)
    canal.queue_bind(COLA_FALLIDOS, EXCHANGE_FALLIDOS)
    for cola, patron in COLAS.items():
        canal.queue_declare(
            cola,
            durable=True,
            arguments={"x-dead-letter-exchange": EXCHANGE_FALLIDOS},
        )
        canal.queue_bind(cola, EXCHANGE, routing_key=patron)
