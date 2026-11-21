# JMS para difundir el inicio de inspecciones del RTV

La propuesta distribuye el evento `InspeccionIniciada` a dos funciones interesadas, auditoría e indicadores. El inicio de una inspección sigue dependiendo de la autorización REST de Turnos y Pagos. La mensajería se plantea para comunicar un hecho registrado, no para sustituir esa autorización.

## 1. Flujo observado en el prototipo

Se toma como referencia el código público del prototipo E1 en la revisión `77d1ff6c38488fc02ab431472621ff4edb59f576` de AppWebRevisionVehicular:

1. `Inspecciones.iniciar` recibe un `turnoId` y una clave de idempotencia.
2. Si la inspección ya existe para la clave, devuelve el registro existente.
3. `ClienteTurnos.autorizar` solicita autorización mediante POST HTTP/JSON a Turnos y Pagos. Aunque usa `sendAsync` internamente, espera el resultado con un plazo; para el flujo de negocio es una interacción de solicitud/respuesta antes de continuar.
4. Después de obtener la autorización, `Inspecciones.iniciar` inserta el inicio y consulta su representación persistida. Devuelve 201 si creó el registro o 200 si la operación recupera uno existente.

Fuentes de código:

- [Inspecciones.java](https://github.com/NixonPiguave/AppWebRevisionVehicular/blob/77d1ff6c38488fc02ab431472621ff4edb59f576/prototipo-e1/src/inspecciones/src/main/java/ec/rtv/e1/Inspecciones.java).
- [ClienteTurnos.java](https://github.com/NixonPiguave/AppWebRevisionVehicular/blob/77d1ff6c38488fc02ab431472621ff4edb59f576/prototipo-e1/src/inspecciones/src/main/java/ec/rtv/e1/ClienteTurnos.java).
- [README del prototipo](https://github.com/NixonPiguave/AppWebRevisionVehicular/blob/77d1ff6c38488fc02ab431472621ff4edb59f576/prototipo-e1/README.md).

Estos archivos no implementan la publicación JMS propuesta aquí. Este análisis no modifica el prototipo del PFC ni su contrato REST.

## 2. Evento propuesto

| Elemento | Diseño inicial |
|---|---|
| Nombre | `InspeccionIniciada` |
| Productor real que se propone | Servicio de Inspecciones, después de confirmar la persistencia del nuevo inicio |
| Destino lógico | Topic `rtv.inspecciones.iniciada` |
| Suscripción A | Auditoría: conservar el hecho de inicio |
| Suscripción B | Indicadores: actualizar un contador o vista de actividad |
| Datos mínimos | `eventoId`, `tipo`, `inspeccionId`, `turnoId`, `autorizacionId`, `ocurridoEn`, `version` |

Auditoría e indicadores son consumidores propuestos para esta exposición; no se presentan como servicios implementados ni requisitos institucionales confirmados. Los identificadores del modelo de origen son UUID para inspección y autorización, y entero para turno. La simulación usa valores ficticios, sin nombres, cédulas ni matrículas.

Jakarta Messaging define la API y sus destinos; un proveedor compatible administra el intercambio. Se utilizan dos suscripciones independientes para que ambas funciones reciban el evento. Una cola con dos consumidores repartiría los mensajes y no demostraría la difusión buscada. La API admite reentregas: recibir un mensaje no garantiza una única ejecución del efecto de negocio. [Especificación 3.1, secciones 2, 4 y 8](https://jakarta.ee/specifications/messaging/3.1/jakarta-messaging-spec-3.1.html).

## 3. Consistencia y fallos

- **Sin autorización confirmada:** Inspecciones no debe generar el evento de inicio a partir de una petición rechazada o de una respuesta incierta.
- **Reintento del inicio:** devolver una inspección existente no debe generar un nuevo hecho de inicio. En la propuesta, el evento mantiene su identidad y se registra solo cuando se crea el inicio.
- **Caída entre guardar y publicar:** dos llamadas consecutivas no forman una transacción atómica. Una futura integración podría registrar la inspección y un evento pendiente en una misma transacción local (outbox) y publicar después. Esto exige modificar y probar esa transacción; el prototipo actual no lo implementa. El patrón se estudia como decisión de diseño, no como garantía ya obtenida.
- **Duplicados:** si el publicador reintenta después de un envío de resultado incierto, puede repetirse la entrega. Cada consumidor necesitaría registrar `eventoId` y coordinar esa marca con su efecto local.
- **Desconexión:** la prueba usa suscripciones no durables para estudiar el mecanismo básico. La retención durante desconexiones requerirá evaluar suscripciones durables, persistencia y expiración. No se afirma recuperación automática.

La autorización y el inicio son operaciones críticas diferentes de la actualización de indicadores. El retraso de un contador puede ser tolerable como elección de diseño; no permite clasificar globalmente el RTV o la API JMS como AP o CP. CAP/PACELC requiere concretar replicación, particiones y comportamiento por dato.

## 4. Diseño de la nueva práctica

La práctica utiliza un productor y dos procesos Java separados, con Apache Artemis 2.57.0 como proveedor. El productor simula un inicio ya guardado y no invoca los servicios del PFC.

- Terminal A: consumidor de auditoría.
- Terminal B: consumidor de indicadores.
- Terminal C: productor del evento ficticio `InspeccionIniciada`.
- Comprobación realizada el 21/11/2026: ambos receptores mostraron el mismo cuerpo y JMSMessageID; el mensaje contiene el mismo eventoId.
- Alcance: imprimir recepciones demuestra transporte y distribución, no una auditoría persistida ni un indicador almacenado.

Los registros reales de auditoría e indicadores están en `evidencias/practica/`. El evento `evt-rtv-001` se recibió con el JMSMessageID `ID:3de6c68e-e573-11f1-ad7c-1edf146250ce` en ambos procesos. La ejecución comprueba difusión a suscripciones activas; no prueba recuperación tras desconexión ni efectos persistidos. Se reprodujo después la práctica y aportó seis capturas; en su ejecución, productor y ambos receptores muestran el JMSMessageID `ID:698a6d8e-e575-11f1-b4d4-e626e217634f` y el mismo cuerpo. Las capturas y los archivos .log corresponden a ejecuciones distintas. Las anteriores de tutorías se conservan sin reutilizarlas como evidencia RTV.

## 5. Integración Java y evaluación

Spring ofrece `JmsTemplate` y contenedores de escucha configurables mediante `@JmsListener`. Su adopción exige configurar proveedor, destino tópico y suscripciones independientes; las anotaciones por sí solas no garantizan la difusión ni la transacción con PostgreSQL. [Documentación de Spring JMS](https://docs.spring.io/spring-framework/reference/integration/jms.html).

Para evaluar el proveedor, se consideran latencia y cantidad de mensajes procesados. Maharjan y colaboradores estudian estas métricas en varios intermediarios, incluido Artemis. Sus resultados no son mediciones del RTV ni prueban una mejora sobre su autorización REST. [Benchmarking Message Queues, Telecom, 2023, DOI 10.3390/telecom4020018](https://www.mdpi.com/2673-4001/4/2/18).

La explicación del outbox se apoya en su propósito general de coordinar el dato local y un evento pendiente. El ejemplo de Microsoft usa Cosmos DB y Service Bus; no es una implementación para PostgreSQL ni se incorpora esa infraestructura al RTV. [Transactional Outbox](https://learn.microsoft.com/en-us/azure/architecture/databases/guide/transactional-out-box-cosmos).


