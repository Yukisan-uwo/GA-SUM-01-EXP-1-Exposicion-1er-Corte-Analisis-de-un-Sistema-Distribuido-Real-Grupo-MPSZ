"""Publica un evento del SSDC (datos ficticios). Uso: python productor.py [routing.key]

Ejemplos: prepostulacion.aprobada (por defecto), prepostulacion.rechazada, proceso.cerrado
"""
import json
import sys
import uuid
from datetime import datetime, timezone

import pika

from comun import EXCHANGE, conectar, preparar

routing_key = sys.argv[1] if len(sys.argv) > 1 else "prepostulacion.aprobada"

conexion = conectar()
canal = conexion.channel()
preparar(canal)
canal.confirm_delivery()  # el broker confirma que recibió el mensaje

# "prepostulacion.aprobada" -> "PrepostulacionAprobada"
tipo = "".join(parte.capitalize() for parte in routing_key.split("."))

evento = {
    "eventoId": str(uuid.uuid4()),
    "tipo": tipo,
    "prepostulacionId": 42,
    "correo": "postulante.demo@example.com",
    "fecha": datetime.now(timezone.utc).isoformat(),
}

canal.basic_publish(
    exchange=EXCHANGE,
    routing_key=routing_key,
    body=json.dumps(evento).encode("utf-8"),
    properties=pika.BasicProperties(
        content_type="application/json",
        delivery_mode=pika.DeliveryMode.Persistent,  # sobrevive a reinicios del broker
        message_id=evento["eventoId"],
    ),
    mandatory=True,
)
print(f"Publicado [{routing_key}] eventoId={evento['eventoId']}")
conexion.close()
