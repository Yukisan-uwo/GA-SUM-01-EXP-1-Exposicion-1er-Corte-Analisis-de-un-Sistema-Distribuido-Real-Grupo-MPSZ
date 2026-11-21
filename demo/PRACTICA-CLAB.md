# Demo MQTT de CLAB

CLAB es el sistema de administración de laboratorios informáticos de la UTEQ.
En el caso de uso CU-04 del informe del proyecto, un administrador marca como
resuelto un reporte de falla. Esta práctica simula el evento que el backend
podría publicar al confirmar ese cambio. Los dos suscriptores representan a
Notificaciones y Auditoría y muestran el mensaje recibido.

## Mosquitto

Desde la raíz del repositorio, iniciar el broker local compartido:

```bash
docker compose -f demo/docker-compose.yml up -d broker
```

El broker escucha en `127.0.0.1:1883` desde el equipo. Usa datos ficticios
en la práctica, ya que el broker de demostración permite el acceso anónimo.

## MQTTX Desktop

1. Crear y conectar tres clientes MQTTX con protocolo `mqtt://`,
   host `127.0.0.1`, puerto `1883` y versión MQTT `5.0`.
   Usar estos identificadores de cliente: `pub-clab`,
   `sub-notificaciones` y `sub-auditoria`.
2. En cada suscriptor, elegir **New Subscription** y registrar
   `clab/fallas/resuelta` con **QoS 1**.
3. En `pub-clab`, seleccionar el formato **JSON**, escribir el mismo
   tópico, elegir **QoS 1** y dejar **Retain** desactivado. Publicar:

   ```json
   {
     "eventoId": "evt-clab-001",
     "tipo": "ReporteFallaResuelto",
     "reporteId": 42
   }
   ```

4. Abrir cada suscriptor y comprobar que muestra el mismo tópico y JSON.
   `eventoId` identifica el evento para que un consumidor pueda detectar
   duplicados. `reporteId` identifica el reporte ficticio.

## Alternativa con MQTTX CLI

Con el broker iniciado, abrir dos terminales y esperar a que ambas confirmen
la suscripción antes de publicar desde una tercera.

```bash
mqttx sub -h 127.0.0.1 -p 1883 -V 5.0 -q 1 -t clab/fallas/resuelta -i sub-notificaciones --verbose
```

```bash
mqttx sub -h 127.0.0.1 -p 1883 -V 5.0 -q 1 -t clab/fallas/resuelta -i sub-auditoria --verbose
```

```bash
mqttx pub -h 127.0.0.1 -p 1883 -V 5.0 -q 1 -t clab/fallas/resuelta -i pub-clab \
  -m '{"eventoId":"evt-clab-001","tipo":"ReporteFallaResuelto","reporteId":42}'
```

Si iniciaste el broker para esta práctica, puedes detenerlo al terminar:

```bash
docker compose -f demo/docker-compose.yml stop broker
```

## Evidencia

En MQTTX Desktop 1.13.3 se publicó un único evento en el tópico
`clab/fallas/resuelta`. Los dos suscriptores lo recibieron con QoS 1.

| Evidencia | Qué muestra |
|---|---|
| [Suscripción](../evidencias/carlos-mqtt/evidencia%20practica/01-suscripcion-qos1.png) | El tópico y QoS 1 en la configuración de MQTTX. |
| [Publicación](../evidencias/carlos-mqtt/evidencia%20practica/02-publicacion-qos1.png) | El evento `ReporteFallaResuelto` publicado por `pub-clab`. |
| [Notificaciones](../evidencias/carlos-mqtt/evidencia%20practica/03-recepcion-notificaciones.png) | Recepción del evento por `sub-notificaciones`. |
| [Auditoría](../evidencias/carlos-mqtt/evidencia%20practica/04-recepcion-auditoria.png) | Recepción del mismo evento por `sub-auditoria`. |
| [Registro del broker](../evidencias/carlos-mqtt/evidencia%20practica/05-registro-mosquitto.txt) | Intercambios SUBACK, PUBLISH y PUBACK de la ejecución en Desktop. |

La [prueba local con MQTTX CLI y Mosquitto](../evidencias/carlos-mqtt/practica-clab.log.txt)
registra el mismo JSON en ambos suscriptores, con QoS 1, y los PUBACK del broker
y los clientes. El alcance de la prueba es el intercambio MQTT con los dos
suscriptores conectados.

- [Guía de MQTTX Desktop](https://mqttx.app/docs/get-started)
- [Documentación de MQTTX CLI](https://mqttx.app/docs/cli)
