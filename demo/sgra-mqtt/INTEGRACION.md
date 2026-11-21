# Integración de la demostración MQTT en el backend SGRA

Esta guía permite reproducir los cambios del experimento en una **copia local** del backend, sin distribuir el proyecto completo ni archivos con credenciales. El código procede del trabajo realizado con Java, Spring Boot, Eclipse Paho, JUnit y Mockito.

## 1. Ubicación de las fuentes

Desde la raíz del backend SGRA:

| Archivo del paquete de demostración | Ubicación original dentro del backend |
|---|---|
| `codigo/MqttPruebaSgra.java` | `src/main/java/com/CLMTZ/Backend/demo/MqttPruebaSgra.java` |
| `codigo/SgraMqttPublisher.java` | `src/main/java/com/CLMTZ/Backend/service/reinforcement/student/SgraMqttPublisher.java` |
| `tests/StudentRequestServiceImplMqttTest.java` | `src/test/java/com/CLMTZ/Backend/service/reinforcement/student/impl/StudentRequestServiceImplMqttTest.java` |
| `tests/StudentRequestServiceBrokerIT.java` | `src/test/java/com/CLMTZ/Backend/service/reinforcement/student/impl/StudentRequestServiceBrokerIT.java` |

**Atención:** JUnit y Mockito deben estar en `src/test/java`. Si un archivo de pruebas se guarda en `src/main/java`, Maven puede informar que no existen esos paquetes.

## 2. Dependencia Maven

El archivo `pom.xml` del backend incorporó Eclipse Paho MQTT v3, versión usada en el experimento:

```xml
<dependency>
    <groupId>org.eclipse.paho</groupId>
    <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
    <version>1.2.5</version>
</dependency>
```

Para las pruebas se utilizan las dependencias de JUnit 5 y Mockito del proyecto de backend. No se debe añadir esas bibliotecas al ámbito de producción solo para corregir errores de ubicación del código de pruebas.

## 3. Cambios en `StudentRequestServiceImpl.java`

Archivo ubicado en:

`src/main/java/com/CLMTZ/Backend/service/reinforcement/student/impl/StudentRequestServiceImpl.java`

En los **imports** de la clase se añaden:

```java
import com.CLMTZ.Backend.service.reinforcement.student.SgraMqttPublisher;
import org.eclipse.paho.client.mqttv3.MqttException;
```

En los atributos, junto a los servicios existentes:

```java
private final SgraMqttPublisher mqttPublisher;
```

La dependencia debe recibirse en el **constructor** y asignarse. El último parámetro agregado es:

```java
SgraMqttPublisher mqttPublisher
```

La asignación correspondiente es:

```java
this.mqttPublisher = mqttPublisher;
```

En `create(...)`, después de llamar al envío de correos y **antes** de devolver `new StudentRequestCreateResponseDTO(requestId)`, se incluyó:

```java
// Integración experimental de mensajería MQTT.
try {
    mqttPublisher.publicarSolicitudCreada(requestId);
} catch (MqttException ex) {
    log.error("No se pudo publicar el evento de la solicitud {}", requestId, ex);
}
```

Este es un **extracto**, no un reemplazo para el archivo del servicio completo. Deben conservarse las demás validaciones, la lógica de registro, archivos adjuntos y correos existentes.

## 4. Configuración del publicador

`SgraMqttPublisher` se inyecta como componente Spring; la configuración documentada utiliza:

```properties
sgra.mqtt.enabled=false
sgra.mqtt.broker=tcp://127.0.0.1:1883
```

El publicador permanece **deshabilitado por defecto**. En P06-C se construyó explícitamente con `new SgraMqttPublisher(true, BROKER)` dentro del test, de modo que el envío al broker real no requiere iniciar la aplicación ni sus servicios externos.

## 5. Ejecutar y verificar

Primero, desde el repositorio de la exposición, levantar Mosquitto:

```powershell
docker compose -f .\demo\docker-compose.yml up -d broker
```

Luego, desde la carpeta del backend local:

```powershell
.\mvnw.cmd -DskipTests compile
.\mvnw.cmd "-Dtest=StudentRequestServiceImplMqttTest" test
.\mvnw.cmd "-Dtest=StudentRequestServiceBrokerIT" test
```

**Resultados documentados el 21-11-2026:** tres pruebas del servicio y una de integración con broker real, todas aprobadas (`Failures: 0`, `Errors: 0`, `BUILD SUCCESS`).

El ensayo de fallo por MQTT utiliza una excepción simulada; por eso puede aparecer `log.error` en consola aunque JUnit finalice correctamente.

## 6. Consideraciones de arquitectura

- El evento se publica al final del método de negocio, pero **no hay garantía transaccional** entre PostgreSQL y Mosquitto; tampoco se garantiza que las notificaciones externas y la publicación MQTT se completen como una unidad.
- Si falla el broker y el repositorio simuló un registro exitoso, el diseño *best-effort* registra el error y permite que el método retorne. Esto no sustituye a reintentos persistentes ni a una cola de salida (*outbox*).
- El ensayo de duplicados usó dos publicaciones manuales con igual `eventoId`; no se verificó la entrega exactamente una vez ni se implementó desduplicación.
- No se ejecutaron pruebas con datos reales de estudiantes o docentes.
