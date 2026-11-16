# Evidencias de la práctica MQTT — Carlos Manotoa

## Objetivo

Demostrar la publicación de un evento y su distribución a dos
suscriptores mediante MQTT, utilizando Mosquitto y MQTTX Desktop.

La práctica simula el evento `SolicitudTutoriaCreada` del SGRA.
No se conecta con el sistema real ni utiliza datos personales.

## Fecha de ejecución

15 de noviembre de 2026, aproximadamente a las 21:49:58.

## Herramientas utilizadas

| Herramienta | Versión |
|---|---|
| Docker Engine | 29.9.3 |
| Docker Compose | 5.7.0 |
| Eclipse Mosquitto | 2.1.2 |
| MQTTX Desktop | 1.13.3 |
| Protocolo MQTT | 5.0 |

Mosquitto se ejecutó en el contenedor `sgra-mqtt-broker`,
utilizando la imagen `eclipse-mosquitto:2`.

## Configuración

| Parámetro | Valor |
|---|---|
| Dirección del broker | `127.0.0.1` |
| Puerto publicado en el equipo | `1883`, limitado a localhost |
| Transporte | MQTT sobre TCP |
| Autenticación | Sin usuario ni contraseña |
| Tópico | `sgra/tutorias/creada` |
| QoS de publicación | `1` |
| QoS solicitado en ambas suscripciones | `1` |
| Retain de la publicación | Desactivado |
| Persistencia del broker | Desactivada |

Se utilizaron tres conexiones independientes en MQTTX:

| Identificador del cliente | Función |
|---|---|
| `pub-sgra` | Publicador del evento |
| `sub-notificaciones` | Suscriptor que representa notificaciones |
| `sub-auditoria` | Suscriptor que representa auditoría |

Los nombres representan los papeles de los clientes de prueba;
no corresponden a servicios del SGRA ya implementados.

## Mensaje publicado

```json
{
  "eventoId": "evt-001",
  "tipo": "SolicitudTutoriaCreada",
  "solicitudId": 42
}
```

`eventoId` identifica el evento y `solicitudId` identifica
la solicitud de tutoría ficticia.

## Procedimiento

1. Iniciar Mosquitto desde la carpeta `demo/`:

   ```bash
   docker compose up -d
   ```

2. Crear las tres conexiones en MQTTX Desktop, utilizando
   MQTT 5.0 y el broker `127.0.0.1:1883`.

3. Conectar `sub-notificaciones` y `sub-auditoria`.
   Suscribir ambos clientes a `sgra/tutorias/creada`
   solicitando QoS 1.

4. Conectar `pub-sgra` y publicar el mensaje en el mismo
   tópico, con QoS 1 y Retain desactivado.

5. Comprobar que los dos suscriptores reciben el evento.

6. Consultar el registro del broker para observar los
   intercambios MQTT.

7. Al terminar, detener la práctica:

   ```bash
   docker compose down
   ```

Para obtener el registro detallado de paquetes mostrado en
las evidencias, durante la prueba se añadieron estas opciones
a la copia local de `mosquitto.conf`:

```conf
log_type all
connection_messages true
```

Después de cambiar esa configuración es necesario reiniciar
el broker:

```bash
docker compose restart broker
```

## Resultado observado

MQTTX registró una publicación desde `pub-sgra` y una recepción
en cada uno de los dos suscriptores.

Los tres registros coincidieron en el tópico y en el contenido
del evento `evt-001`, con QoS 1 y Retain desactivado.

El registro de Mosquitto mostró:

- La conexión de los tres clientes mediante MQTT 5.0.
- Las suscripciones de ambos receptores y sus confirmaciones SUBACK.
- La recepción del PUBLISH enviado por `pub-sgra`.
- El envío de un PUBLISH a cada suscriptor.
- El PUBACK enviado al publicador.
- Los PUBACK recibidos de ambos suscriptores.

## Capturas

| Archivo | Evidencia |
|---|---|
| `01-configuracion-broker.png` | Versiones de Docker y Compose, estado del contenedor, puerto y configuración de Mosquitto. |
| `02-configuracion-publicador.png` | Dirección del broker, puerto, identificador del publicador y versión MQTT. |
| `03-suscripcion-notificaciones.png` | Tópico y QoS solicitados por el cliente de notificaciones. |
| `04-suscripcion-auditoria.png` | Tópico y QoS solicitados por el cliente de auditoría. |
| `05-publicacion.png` | Publicación del evento registrada por MQTTX. |
| `06-recepcion-notificaciones.png` | Recepción del evento en `sub-notificaciones`. |
| `07-recepcion-auditoria.png` | Recepción del evento en `sub-auditoria`. |
| `08-registro-broker.png` | Conexiones, suscripciones, publicaciones y confirmaciones observadas por Mosquitto. |

Las capturas proceden de la ejecución real. Las imágenes de
recepción muestran una región de la interfaz de MQTTX para
facilitar la lectura del tópico, QoS y mensaje.

## Alcance de la comprobación

La prueba demuestra la distribución de una publicación a dos
suscriptores conectados y los intercambios QoS 1 observados
durante esa ejecución.

No se realizaron pruebas de pérdida de conectividad, reinicio
del broker, recuperación de mensajes ni generación de duplicados.
Tampoco se midió la latencia.

Los clientes representan notificaciones y auditoría, pero no
ejecutan esas operaciones de negocio. La recepción del mensaje
no demuestra que se haya enviado una notificación o registrado
una auditoría en el SGRA.

## Documentación técnica

- [Documentación de Eclipse Mosquitto](https://mosquitto.org/documentation/)
- [Guía de MQTTX Desktop](https://mqttx.app/docs/get-started)
- [Especificación MQTT 5.0 de OASIS](https://docs.oasis-open.org/mqtt/mqtt/v5.0/os/mqtt-v5.0-os.html)

## Apoyo de herramientas

Codex se empleó únicamente como asistente para la instalación de herramientas y la redacción del informe. Tanto las pruebas en MQTTX como la captura de evidencias y la validación en los registros de Mosquitto fueron ejecutadas manualmente.
