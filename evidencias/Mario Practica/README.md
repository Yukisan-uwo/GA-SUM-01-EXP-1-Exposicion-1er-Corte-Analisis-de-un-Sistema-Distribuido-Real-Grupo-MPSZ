# Evidencia de práctica PUB/SUB — Mario Zambrano

**Actividad:** GA-SUM-01 / EXP-1  
**Tema:** Fundamentos de Publish/Subscribe  
**Proyecto de aplicación:** SGRA  
**Responsable:** Zambrano Parraga Luis Mario

## 1. Objetivo

Comprobar el funcionamiento del patrón Publish/Subscribe mediante la publicación de un mensaje y su recepción por dos suscriptores independientes, utilizando Eclipse Mosquitto como broker MQTT.

## 2. Escenario de prueba

Se simuló un evento de creación de tutoría del SGRA mediante tres terminales:

- Terminal A: cliente suscriptor que representa notificaciones.
- Terminal B: cliente suscriptor que representa auditoría.
- Terminal C: cliente publicador que simula el origen del evento.

Los dos suscriptores utilizaron el tópico `sgra/tutorias/creada`.

## 3. Procedimiento realizado

Se inició el broker Mosquitto mediante Docker y se establecieron las conexiones de los dos suscriptores.
Posteriormente, desde la terminal publicadora se enviaron mensajes ficticios relacionados con solicitudes de tutoría.
Se comprobaron las respuestas observadas en las terminales receptoras y se conservaron capturas de pantalla de la ejecución.

## 4. Resultados observados

Durante la prueba, ambos suscriptores recibieron los mensajes publicados en el mismo tópico.
Esto permitió comprobar la distribución de mensajes a múltiples consumidores y el funcionamiento de un intermediario de mensajería.
Las capturas disponibles muestran el broker activo, las terminales en espera, la publicación y la recepción de los mensajes.

## 5. Limitaciones

La práctica utilizó clientes MQTT independientes y no ejecutó el backend original del SGRA.
Por tanto, los resultados no demuestran que la aplicación tenga mensajería PUB/SUB integrada actualmente.
Tampoco se comprobaron mecanismos de recuperación ante fallos, persistencia de mensajes ni garantías de entrega durante desconexiones.

## 6. Próxima etapa: aplicación al SGRA

Se propone estudiar la integración de la publicación de eventos en el backend Java Spring Boot del SGRA.
El objetivo es que, después de registrar una solicitud académica, el backend pueda generar un evento como `SolicitudTutoriaCreada`.
Posteriormente, dos consumidores independientes recibirían el evento a través de un broker.
Esta integración requiere implementación, configuración y pruebas adicionales.

## 7. Evidencias

Las capturas originales de la ejecución se encuentran en esta misma carpeta.
Estas imágenes corresponden a la práctica realizada y se conservan como evidencia del comportamiento observado.

## 8. Uso de inteligencia artificial

Se utilizó ChatGPT (GPT-6) como apoyo para organizar la documentación y explicar el alcance de la prueba.
