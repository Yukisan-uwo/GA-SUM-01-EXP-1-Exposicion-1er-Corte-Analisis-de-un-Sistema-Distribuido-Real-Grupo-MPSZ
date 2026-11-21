# Práctica experimental PUB/SUB — SGRA con Java y Mosquitto

**Responsable:** Mario Zambrano · **Fecha:** 21 de noviembre de 2026  
**Asignatura:** Aplicaciones Distribuidas · **Ámbito:** demostración local, no despliegue productivo.

## Objetivo

Demostrar el patrón **Publish/Subscribe** a través de eventos `SolicitudTutoriaCreada` del SGRA. El backend experimental actúa como publicador MQTT; el broker Eclipse Mosquitto distribuye un mensaje a suscriptores independientes de **Notificaciones** y **Auditoría**.

- Broker local: `tcp://127.0.0.1:1883`
- Tópico: `sgra/tutorias/creada`
- Nivel de servicio del mensaje: **MQTT QoS 1**; `retained=false`.
- Campos del mensaje: `eventoId`, `tipo`, `solicitudId`, `fecha`.

## Contenido

```text
demo/sgra-mqtt/
├── README.md
├── INTEGRACION.md
├── codigo/
│   ├── MqttPruebaSgra.java
│   └── SgraMqttPublisher.java
└── tests/
    ├── StudentRequestServiceImplMqttTest.java
    └── StudentRequestServiceBrokerIT.java
```

**Nota:** Las clases Java se copian desde el backend local en el que fueron compiladas y probadas. Estas clases conservan sus declaraciones `package` originales: se presentan como **fuentes de referencia para incorporar al backend**, no como un proyecto Maven independiente.

## Preparación y reproducción

Se requiere Java y Maven Wrapper del **repositorio original del backend SGRA**, más Docker con Mosquitto. En el repositorio de la exposición, iniciar el broker:

```powershell
docker compose -f .\demo\docker-compose.yml up -d broker
```

Si PowerShell restringe scripts, se pueden copiar manualmente los cuatro archivos siguiendo las rutas detalladas en `INTEGRACION.md`; no es necesario alterar las políticas de ejecución.

Desde la raíz del **backend local** con las modificaciones experimentales incorporadas:

```powershell
.\mvnw.cmd -DskipTests compile
.\mvnw.cmd "-Dtest=StudentRequestServiceImplMqttTest" test
.\mvnw.cmd "-Dtest=StudentRequestServiceBrokerIT" test
```

Para la prueba con broker real, Mosquitto debe estar funcionando en `127.0.0.1:1883`. Se puede observar en terminales de la exposición mediante:

```powershell
docker exec -it sgra-mqtt-broker mosquitto_sub -i sgra-notificaciones -q 1 -t sgra/tutorias/creada -v
docker exec -it sgra-mqtt-broker mosquitto_sub -i sgra-auditoria -q 1 -t sgra/tutorias/creada -v
```

## Resultados comprobados

| Identificador | Escenario | Observación |
|---|---|---|
| P01–P02 | Java publica; dos clientes consumen | Ambos recibieron un mismo evento |
| P03 | Broker detenido | Error de conexión MQTT `32103` |
| P04 | Broker reiniciado | Las **nuevas** publicaciones volvieron a entregarse |
| P05 | Dos publicaciones manuales con igual `eventoId` | Los clientes recibieron ambas; **no** es una prueba de reentrega automática |
| P06-A/B | Pruebas del servicio con JUnit y Mockito | 3 tests, 0 fallos, 0 errores |
| P06-C | Servicio Java + broker real, persistencia simulada | 1 test, 0 fallos, 0 errores; recepción por dos suscriptores |

Las capturas originales y el análisis están en `evidencias/Mario Practica/practica-grande-SGRA/`.

## Límites del experimento

- En P06-C **el repositorio de persistencia se simuló con Mockito**; no se registró ninguna solicitud en PostgreSQL real. No se inició Angular ni se utilizaron cuentas o correos reales.
- La publicación integrada es de tipo **best-effort** y no garantiza atomicidad con la transacción de base de datos. No existe aquí un *Transactional Outbox* ni reintentos persistentes.
- Se reciben mensajes duplicados si el productor los publica dos veces, pero **no se implementó idempotencia** en los consumidores.
- Las pruebas de caída y recuperación son fallos **inducidos localmente**, no un incidente histórico documentado del SGRA.

Para la modificación puntual de `StudentRequestServiceImpl` y su dependencia Maven, consultar `INTEGRACION.md`.
