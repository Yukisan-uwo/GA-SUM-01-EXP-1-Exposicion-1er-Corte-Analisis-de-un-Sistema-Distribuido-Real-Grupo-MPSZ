# Revisión de las fuentes del aporte JMS

Comprobación técnica realizada por mi el 21/11/2026 contra las páginas oficiales. Se mantienen las claves bibliográficas existentes y no se reincorpora como referencia el repositorio RTV pero dejo el link del repositorio de el proyecto aqui https://github.com/NixonPiguave/AppWebRevisionVehicular.git.

| Clave | Datos comprobados | Uso en esta sección |
|---|---|---|
| `jakarta2022` | Jakarta Messaging Team, Jakarta Messaging 3.1, publicación final del 25/02/2022. Especificación oficial, sin DOI. | API, proveedores, colas y tópicos. Secciones 4.1 y 4.2; suscripciones no durables, 8.3.1; recepción bloqueante, 8.5; entrega, 7.7. |
| `springjms` | Spring, «JMS (Java Message Service)», documentación oficial consultada el 21/11/2026. Sin DOI ni fecha de publicación inventada. | `JmsTemplate`, contenedores y `@JmsListener` como alternativa de integración futura. No describe el código de la demo ni el framework actual del RTV. |
| `maharjan2023` | Rokin Maharjan; Md Showkat Hossain Chy; Muhammad Ashfakur Arju; Tomas Cerny. «Benchmarking Message Queues». Telecom, 2023, vol. 4, núm. 2, pp. 298–312. DOI: `10.3390/telecom4020018`. Publicado el 13/06/2023. El enlace DOI conduce a la misma obra de la editorial. | Motiva evaluar latencia y throughput. No se trasladan sus resultados al RTV ni se afirma que el sistema haya mejorado su rendimiento. |

## Enlaces de comprobación

- [Jakarta Messaging 3.1](https://jakarta.ee/specifications/messaging/3.1/jakarta-messaging-spec-3.1.html).
- [Spring JMS](https://docs.spring.io/spring-framework/reference/integration/jms.html).
- [Artículo en la editorial](https://www.mdpi.com/2673-4001/4/2/18).
- [DOI del artículo](https://doi.org/10.3390/telecom4020018).

Las obras fechadas en 2022 y 2023 están dentro de los últimos cinco años respecto a noviembre de 2026. La documentación Spring es una página viva sin fecha editorial en esta entrada: la fecha de consulta no debe presentarse como fecha de publicación. Las referencias científicas recientes exigidas por la guía deben acreditarse con las publicaciones del conjunto del equipo.

## Lectura y evidencia aportada

El archivo `maharjan2023-benchmarking-message-queues.pdf` es el PDF original descargado de la editorial.

1. Revisar la primera página para reconocer título, autores y DOI.
2. Leer la sección 4, «Methodology», en la **página 6 del PDF / página impresa 303**, sobre las métricas de evaluación.
3. Leer la sección 7, «Discussion», en la **página 13 del PDF / página impresa 310**, para reconocer el alcance de la comparación.
4. Subrayar personalmente un fragmento pertinente y escribir una observación propia sobre cómo orienta una evaluación futura del RTV. La observación debe distinguir los resultados del artículo de los de la práctica.
5. Tomar una captura con el fragmento y la página visibles. Guardarla, por ejemplo, como `Nixon Piguave/evidencias/lectura/01-Maharjan-2023-pagina-303-anotada.png` si se usa la página 303.

La guía exige al menos tres capturas de artículos anotados por el equipo, no tres obligatorias por integrante. La captura personal anterior sería un aporte adicional y una ayuda para defender la referencia. 

## Actualización — 21/11/2026

Se aportó la captura `../evidencias/lectura/01-Maharjan-2023-pagina-303-anotada.png`, con la página 303 visible, la frase sobre latencia y throughput subrayada y una anotación sobre el RTV. El README de esa evidencia aclara que la distribución observada se comprobó en las terminales.
