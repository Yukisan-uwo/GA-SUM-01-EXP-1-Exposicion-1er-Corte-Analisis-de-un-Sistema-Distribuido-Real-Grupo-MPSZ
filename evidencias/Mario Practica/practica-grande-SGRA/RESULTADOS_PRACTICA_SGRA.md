# RESULTADOS — práctica Java / MQTT del SGRA

**Responsable:** Mario Zambrano  
**Fecha de pruebas:** 21 de noviembre de 2026  
**Tópico:** `sgra/tutorias/creada`  
**Broker:** Eclipse Mosquitto local en Docker  
**Mensaje:** `SolicitudTutoriaCreada`, datos ficticios de solicitud 42

| Código | Propósito | Resultado observado | Alcance |
|---|---|---|---|
| P01 | Publicar evento desde una clase Java | Publicación completada, salida 0 | Java cliente MQTT; no crea tutoría real |
| P02 | Difundir evento a dos suscriptores | Ambos mostraron el mismo `eventoId` y JSON | Dos consumidores conectados |
| P03 | Desconectar broker | `Connection refused` / 32103 | Sin reintento ni cola de salida demostrados |
| P04 | Reiniciar y reconectar | Una nueva publicación fue recibida | No prueba recuperar la publicación fallida |
| P05 | Repetir `eventoId` en dos envíos | Dos recepciones por cada suscriptor con igual ID lógico | Repetición inducida; sin deduplicación |
| P06-A/B | Simular éxito, fallo de persistencia y fallo MQTT | 3 tests JUnit/Mockito aprobados | No usa base de datos ni correos reales |
| P06-C | Invocar el método real del servicio y publicar en broker real | 1 test aprobado y 2 terminales externas muestran mismo evento | Repositorio simulado; sin persistencia real |

## Interpretación

Los resultados muestran que un servicio Java puede desacoplar la difusión de un evento hacia varios consumidores mediante Mosquitto. La caída del broker impidió la publicación y la recuperación solo permitió enviar **eventos nuevos**. Repetir un ID lógico generó procesamiento potencialmente duplicable en los receptores, de modo que un consumidor real necesitaría control idempotente. La prueba P06-C recorrió el método `create()` del servicio con dependencias de infraestructura simuladas y un broker real. No se debe comunicar que SGRA ya dispone de una solución de mensajería productiva.

## Pendientes si se busca integración extremo a extremo

- Preparar PostgreSQL local con datos ficticios y aislar Azure, correo y otras integraciones.
- Comprobar persistencia real y publicación condicionada a transacción confirmada.
- Considerar patrón *transactional outbox*, reintentos persistentes y deduplicación.
- Conservar el código experimental o instrucciones de reproducción en una ubicación apropiada sin secretos.
