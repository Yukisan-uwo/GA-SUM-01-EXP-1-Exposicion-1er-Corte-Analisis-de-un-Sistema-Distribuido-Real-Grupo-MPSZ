"""Consumidor de una cola. Uso: python consumidor.py q.correo [--falla]

--falla: simula un error de procesamiento; el mensaje se rechaza sin reencolar
y el broker lo envía a la cola de fallidos (q.fallidos).
"""
import json
import sys

from comun import COLAS, COLA_FALLIDOS, conectar, preparar

if len(sys.argv) < 2 or sys.argv[1] not in list(COLAS) + [COLA_FALLIDOS]:
    sys.exit("Uso: python consumidor.py <q.correo|q.notificaciones|q.auditoria|q.fallidos> [--falla]")

cola = sys.argv[1]
simular_falla = "--falla" in sys.argv

conexion = conectar()
canal = conexion.channel()
preparar(canal)
canal.basic_qos(prefetch_count=1)


def al_recibir(ch, metodo, props, cuerpo):
    evento = json.loads(cuerpo)
    print(f"[{cola}] recibido {evento['tipo']} eventoId={evento['eventoId']}"
          f" (routing key: {metodo.routing_key})")
    if simular_falla:
        print(f"[{cola}] FALLA simulada: se rechaza sin reencolar")
        ch.basic_nack(metodo.delivery_tag, requeue=False)
    else:
        ch.basic_ack(metodo.delivery_tag)  # confirmación manual después de procesar


canal.basic_consume(cola, al_recibir, auto_ack=False)
print(f"[{cola}] esperando mensajes. Ctrl+C para salir.")
try:
    canal.start_consuming()
except KeyboardInterrupt:
    pass
conexion.close()
