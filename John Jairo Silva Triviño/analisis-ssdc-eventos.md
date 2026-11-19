# Análisis del SSDC: dónde hoy hay un "evento" escondido

**Autor:** Silva Triviño John Jairo · **Fecha:** 18 de noviembre de 2026
**Fuente:** código del SSDC, repositorio https://github.com/aldhair00/CHRSweb (carpeta `backend/src/main/java/org/uteq/backend`).

## 1. Hallazgo

Cuando un revisor aprueba una prepostulación, el backend hace tres cosas seguidas en el mismo proceso y la misma petición:

| Paso | Dónde está en el código | Cómo se ejecuta hoy |
|---|---|---|
| Cambiar el estado y crear el usuario del postulante | `PrepostulacionService.actualizarEstado` (anotado `@Transactional`, línea ~336) | Síncrono, dentro de la transacción |
| Enviar credenciales por correo | `emailService.enviarCredenciales(...)` en `PrepostulacionService` (línea ~446); el método está en `EmailService` con `@Async` | Hilo del mismo backend (`@EnableAsync` en `BackendApplication`) |
| Crear la notificación interna | `notifService.notifPostulanteAprobado(...)` (líneas ~416 y ~449) → `NotificacionService.notificarUsuario` llama a `sp_crear_notificacion` por `JdbcTemplate` | Síncrono; si falla, se registra el error y se sigue |

Además, la auditoría del sistema se hace con disparadores (*triggers*) en PostgreSQL, según el documento técnico del SSDC.

## 2. Observaciones verificables en el código

1. El método `enviarCredenciales` es `@Async` y de tipo `void`: quien lo llama no se entera si el envío falla. Dentro lanza `RuntimeException` ante un `MessagingException`.
2. Justo después de llamarlo, `PrepostulacionService` escribe en el log `"Correo de credenciales enviado a ..."` (línea ~447). Ese mensaje aparece **antes** de que el envío ocurra realmente.
3. En el código del backend no aparece ningún broker, cola ni reintento: la búsqueda de `Queue`, `@Retryable`, `rabbit`, `kafka`, `jms` y `amqp` en `backend/src` no devuelve resultados, y `pom.xml` no tiene esas dependencias. El documento técnico menciona un "sistema de colas para notificaciones masivas" (Sprint 6); en el código lo único parecido es `@Async`. Confirmarlo con Aldair antes de la exposición.
4. **Inferencia (no probada):** si el backend se detiene mientras un envío `@Async` está pendiente, ese correo se pierde sin dejar rastro. Para afirmarlo habría que reproducirlo; en la exposición se dice "puede perderse", no "se pierde".

## 3. El evento y cómo quedaría con AMQP (propuesta futura)

- **Evento:** `PrepostulacionAprobada` con `eventoId`, `prepostulacionId`, `fecha` y el destinatario (datos mínimos; nunca la clave temporal en el mensaje).
- **Exchange:** `ssdc.eventos`, tipo `topic`. **Routing key:** `prepostulacion.aprobada`.
- **Colas durables:** `q.correo` (credenciales y rechazos), `q.notificaciones` (avisos dentro del sistema), `q.auditoria` (todo).
- **Fallos:** *ack* manual, cola `q.fallidos` (dead-letter) y reintentos acotados.
- **Cuidado de consistencia:** publicar el evento *después* de confirmar la transacción de PostgreSQL (patrón *transactional outbox*), para no avisar algo que luego se revierte.

## 4. Lo que NO afirmamos

- Que el SSDC use hoy un broker: no lo usa.
- Cifras de disponibilidad o rendimiento del documento del SSDC (sin evidencia).
- Que RabbitMQ sea "más rápido" que MQTT en general: depende del escenario; solo se cita lo que digan las fuentes verificadas.

## 5. Conexión con CAP/PACELC (para el grupo)

Notificaciones y correo toleran retraso: se prioriza disponibilidad (AP) con consistencia eventual. Los datos del proceso (estado de la prepostulación, usuarios) deben mantenerse consistentes (CP) en PostgreSQL. El broker no sustituye a la base de datos.
