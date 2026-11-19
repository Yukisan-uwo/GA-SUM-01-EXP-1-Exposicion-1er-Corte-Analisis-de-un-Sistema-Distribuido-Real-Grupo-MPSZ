# Parte de John Silva — EXP-1 (AMQP y análisis comparativo, aplicado al SSDC)

Aplicamos la mensajería publish/subscribe a nuestros proyectos finales como si fueran distribuidos. Mi proyecto es el **SSDC** (Sistema de Selección y Contratación de Docentes). El SSDC actual es un monolito por capas **sin broker**; lo que aquí se presenta es una propuesta de evolución, no algo implementado.

## Contenido de esta carpeta
| Archivo | Para qué sirve |
|---|---|
| `analisis-ssdc-eventos.md` | Análisis del código del SSDC y del evento `PrepostulacionAprobada` |
| `fuentes-john.md` | Fuentes y estado de verificación |
| `practica-rabbitmq/` | Práctica con RabbitMQ y Python (pasos en su README) |
| `evidencias/` | Capturas de la práctica y de las fuentes |
| `seccion-john.tex` | Diapositivas 10 a 12 (se integran en `secciones/john.tex`) |
| `referencias-john.bib` | Entradas bibliográficas nuevas |

## Diapositivas
10. AMQP y RabbitMQ: exchange, binding y cola (diagrama propio).
11. Comparación MQTT vs JMS vs AMQP y tolerancia a fallos.
12. Recomendación para el SSDC, compromisos y lección de un incidente real.
