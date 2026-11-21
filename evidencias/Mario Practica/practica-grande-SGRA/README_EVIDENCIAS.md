# Evidencias de Mario Zambrano — PUB/SUB en SGRA

**Fecha de ejecución documentada:** 21 de noviembre de 2026  
**Capturas incluidas:** 20 archivos PNG originales, renombrados y organizados; sin retocar sus contenidos.  
**Objetivo:** documentar la publicación de eventos MQTT desde Java y desde el método de servicio del SGRA, su difusión, escenarios de fallo y límites.

## Índice de capturas

| Archivo | Qué evidencia |
|---|---|
| `00_preparacion/01-entorno-java-maven-docker.png` | Comprobación de versiones y preparación de código para revisión. |
| `01_broker/02-broker-mosquitto-iniciado.png` | Arranque de Mosquitto en Docker y estado del servicio. |
| `01_broker/03-conexion-puerto-y-logs.png` | Puerto 1883 accesible y registros de Mosquitto. |
| `02_P01_P02_mensajeria/04-publicador-java-ejecucion-correcta.png` | La clase Java MqttPruebaSgra publica SolicitudTutoriaCreada correctamente. |
| `02_P01_P02_mensajeria/05-ambos-suscriptores-reciben.png` | Dos terminales reciben el mismo evento con el mismo eventoId. |
| `03_P03_fallo/06-error-conexion-con-broker-detenido.png` | Con el broker detenido, el publicador falla con Connection refused. |
| `03_P03_fallo/07-contenedor-detenido.png` | Estado Exited del contenedor durante la prueba de fallo. |
| `04_P04_recuperacion/08-publicacion-java-tras-reinicio.png` | Publicación nueva después de reiniciar Mosquitto. |
| `04_P04_recuperacion/09-recepcion-tras-reinicio.png` | Ambos consumidores reciben la nueva publicación. |
| `04_P04_recuperacion/10-broker-reiniciado-docker.png` | Docker confirma que Mosquitto volvió a estar activo. |
| `05_P05_duplicados/11-primer-envio-evento-id-fijo.png` | Primera publicación manual con eventoId repetible. |
| `05_P05_duplicados/12-suscriptores-primer-envio.png` | Recepción de la primera publicación con ID fijo. |
| `05_P05_duplicados/13-segundo-envio-evento-id-fijo.png` | Segunda publicación manual usando el mismo eventoId. |
| `05_P05_duplicados/14-suscriptores-dos-envios.png` | Los receptores muestran ambas publicaciones con igual eventoId. |
| `06_P06_test_codigo/15-revision-pruebas-codigo.png` | Pantalla del código de pruebas con JUnit y Mockito. |
| `06_P06_test_codigo/16-correccion-ubicacion-test.png` | Pantalla del diagnóstico del test en su ubicación inicial incorrecta; no es ejecución exitosa. |
| `06_P06_test_codigo/17-tres-pruebas-unitarias-pasadas.png` | Maven: tres pruebas automatizadas, cero fallos, cero errores y BUILD SUCCESS. |
| `07_P06C_servicio_broker/18-p06c-test-integracion-exitoso.png` | JUnit valida servicio SGRA→publicador MQTT→dos suscriptores; una prueba aprobada. |
| `07_P06C_servicio_broker/19-p06c-recepcion-en-terminales.png` | Notificaciones y Auditoría reciben el evento de P06-C con el mismo eventoId. |

## Qué se ha comprobado realmente

- **P01 y P02 — publicación y distribución:** una clase Java de prueba dentro del backend publicó `SolicitudTutoriaCreada` en `sgra/tutorias/creada`; dos clientes MQTT conectados recibieron el mismo mensaje.
- **P03 — caída de Mosquitto:** el broker se detuvo intencionadamente y el publicador encontró `Connection refused` / código MQTT `32103`.
- **P04 — reinicio:** al reiniciar Mosquitto y restablecer las suscripciones se logró una nueva publicación y recepción. **No** se demostró reenvío automático del mensaje fallido.
- **P05 — duplicados lógicos:** se ejecutó manualmente dos veces el publicador con `eventoId=evt-sgra-duplicado-001`; los suscriptores mostraron ambos mensajes. **No** se demostró retransmisión automática ni idempotencia.
- **P06-A/B — pruebas automatizadas simuladas:** Maven ejecutó tres pruebas JUnit/Mockito, sin errores ni fallos. Validan las llamadas al publicador y el tratamiento de errores según el código experimental, con repositorios y correo simulados.
- **P06-C — servicio y broker reales con repositorio simulado:** `StudentRequestServiceImpl.create()` activó el `SgraMqttPublisher` real; JUnit se conectó a Mosquitto con dos clientes consumidores y recibió el evento `d0ebf368-b6be-4277-81bc-ebd39244752b`. Resultado: **1 prueba, 0 fallos, 0 errores, `BUILD SUCCESS`**. Las dos terminales externas también muestran la recepción del mismo ID.

## Límites y precauciones para la exposición

1. **No se ejecutó PostgreSQL real en P06-C.** Mockito simuló el registro de la solicitud y devolvió el ID ficticio 42. Tampoco se arrancó Angular ni se enviaron correos reales.
2. **La publicación del servicio es experimental y best-effort.** Un fallo de MQTT puede dejar una solicitud registrada sin generar avisos; el código probado todavía no tiene *transactional outbox*, reintentos persistentes ni deduplicación de consumidores.
3. **QoS 1 no equivale a exactamente una vez en la operación de negocio.** La prueba P05 demuestra la necesidad de identificar duplicados, no una garantía de corrección implementada.
4. **Los fallos P03–P04 son ensayos locales controlados.** No reemplazan el incidente histórico real y el análisis de causa raíz exigidos por la rúbrica escrita.
5. **El test JUnit imprime un error MQTT intencionado** al simular el escenario de fallo: pese a ese log, el resumen indica `Failures: 0, Errors: 0` y `BUILD SUCCESS`.
6. **No modificar el despliegue del SGRA ni compartir credenciales.** La práctica utiliza datos ficticios y un broker local para experimentar.

## Reproducción de los tests

Desde el directorio raíz del backend local **con las clases experimentales ya incorporadas** (esas clases no forman parte de este ZIP de capturas):

```powershell
.\mvnw.cmd "-Dtest=StudentRequestServiceImplMqttTest" test
.\mvnw.cmd "-Dtest=StudentRequestServiceBrokerIT" test
```

Para P06-C, Mosquitto debe estar en ejecución y accesible en `tcp://127.0.0.1:1883`. El archivo fuente Java experimental y su dependencia Eclipse Paho deben conservarse por separado para reproducir el experimento.