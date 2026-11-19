# Fuentes de John (estado de verificación)

Regla: no se cita nada que no hayas abierto tú. Antes de entregar, abre cada enlace, comprueba título/autores/año y rellena la fecha de consulta.

| Clave en `.bib` | Fuente | DOI / URL | Estado |
|---|---|---|---|
| `derlatka2023` (ya está en `referencias.bib`) | Derlatka y Kopniak, *Comparative analysis of transfer protocols asynchronous messages on systems queuing*, J. Computer Sciences Institute, 2023 | 10.35784/jcsi.3074 | Aparece en DOAJ y en la web de la revista; **John debe leerla y capturar fragmento con página** |
| `maharjan2023` (ya está) | Maharjan et al., *Benchmarking Message Queues*, Telecom 4(2), 2023 | 10.3390/telecom4020018 | El DOI redirige a la página de MDPI (comprobado); contenido **sin leer: John lo lee** |
| `bezerra2024` (ya está) | dos Reis Bezerra et al., *Performance of security options for message protocols*, Int. J. Network Management, 2024 | 10.1002/nem.2274 | **John debe abrirla y leerla** |
| `gultunca2018` (nueva) | Gültunca y Zaim, *Examination and Comparison of the Communication Protocols on the Application Layer in IoT*, Istanbul Commerce Univ. J. Sci. 17(33), pp. 41–50, 2018 | http://dergipark.gov.tr/ticaretfbd (sin DOI) | Leída y comprobada: compara MQTT (Mosquitto) y AMQP (RabbitMQ); concluye que AMQP transmite más rápido (p. 41, 49). Límites: 1000 mensajes, 5 tamaños, Raspberry Pi 3, LAN (p. 48). Sirve para la postura crítica |
| `oasis2012` (nueva) | OASIS, *AMQP Version 1.0* | https://docs.oasis-open.org/amqp/core/v1.0/os/amqp-core-overview-v1.0-os.html | **No pude abrirla desde mi entorno: ábrela tú** |
| `rabbitmq_concepts` (nueva) | RabbitMQ, *AMQP 0-9-1 Model Explained* | https://www.rabbitmq.com/tutorials/amqp-concepts | **No pude abrirla: ábrela tú** |
| `github2018` (nueva) | Warner, *October 21 post-incident analysis*, GitHub Blog, 30-oct-2018 | https://github.blog/2018/10/30/oct21-post-incident-analysis/ | Leída y comprobada (22:52 UTC corte de red de 43 s; >5 millones de eventos de webhooks pendientes; unas 200 000 cargas expiraron por TTL; 24 h 11 min de servicio degradado) |

## Capturas que debes hacer (3 mínimo, con la frase resaltada y la página)
1. Gültunca y Zaim, p. 49: la frase donde concluyen que AMQP transmite más rápido que MQTT.
2. Derlatka y Kopniak o Maharjan: el fragmento que usarás para comparar (elige tú).
3. GitHub 2018: el párrafo del backlog con el TTL.
Guárdalas en `john/evidencias/` con nombre `fuente-<clave>-p<página>.png`.

## Pendiente de decisión del grupo
El incidente real del grupo puede ser GitHub 2018 (lo analizó Aldair en FORM-AC-01 y encaja con colas) o Google Cloud 2019 (el tuyo). Cualquiera se cita con su informe oficial.
