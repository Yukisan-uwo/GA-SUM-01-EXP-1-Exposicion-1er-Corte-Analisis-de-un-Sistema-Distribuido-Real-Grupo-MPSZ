# Guion de exposición — Mario Zambrano

## Diapositiva 1. SGRA: punto de partida

Para comenzar, vamos a utilizar como caso de aplicación nuestro proyecto SGRA, el Sistema de Gestión de Refuerzo Académico.

Este proyecto busca facilitar la gestión de tutorías entre estudiantes y docentes, mediante una aplicación web que permite organizar solicitudes y sesiones de refuerzo académico.

Como observamos en el diagrama, el proyecto cuenta con una arquitectura cliente-servidor. En la capa de presentación tenemos Angular, responsable de la interacción con el usuario; en la capa de negocio utilizamos Spring Boot, que expone una API REST; y para la persistencia de información se utiliza PostgreSQL.

Esta estructura permite separar responsabilidades, pero nos plantea una oportunidad de mejora cuando distintos componentes necesitan reaccionar ante un mismo acontecimiento.

Por ejemplo, cuando un estudiante registra una solicitud de tutoría, podríamos necesitar notificar al docente, registrar una auditoría y actualizar indicadores académicos.

Una alternativa sería realizar llamadas directas a cada componente. Sin embargo, esto puede generar dependencias adicionales: el componente que registra la solicitud tendría que conocer a los demás y gestionar sus posibles errores.

**Aquí surge nuestra pregunta: ¿cómo podemos comunicar un mismo acontecimiento a varios componentes sin conectarlos directamente entre sí?**

Esa necesidad nos lleva al patrón Publish/Subscribe.

## Diapositiva 2. PUB/SUB: un evento, varios receptores

Publish/Subscribe, conocido como PUB/SUB, es un patrón de comunicación que permite distribuir mensajes entre componentes mediante un mecanismo de publicación y suscripción.

A diferencia de una comunicación directa, el publicador no necesita conocer la dirección ni la identidad de cada consumidor.

En el diagrama tenemos tres elementos fundamentales.

Primero está el **publicador**, que produce un mensaje cuando ocurre un acontecimiento. En nuestro ejemplo, sería el componente encargado de registrar solicitudes en el SGRA.

Segundo tenemos el **broker**, que actúa como intermediario y distribuye los mensajes utilizando tópicos o reglas de enrutamiento.

Finalmente, están los **suscriptores**, que expresan su interés en recibir determinados mensajes.

Imaginemos que se genera el evento `SolicitudTutoriaCreada`. El publicador envía ese evento al broker y este lo distribuye a los componentes suscritos: notificaciones, auditoría e indicadores.

La ventaja principal es el **desacoplamiento**. El publicador no necesita saber cuántos consumidores existen ni cómo están implementados.

También hablamos de comunicación asíncrona porque el publicador puede continuar su procesamiento sin esperar que todos los consumidores finalicen sus tareas.

Sin embargo, esto introduce nuevos desafíos: necesitamos controlar posibles pérdidas de mensajes, duplicados, retrasos y fallos del broker.

Por tanto, PUB/SUB no elimina la complejidad; cambia la manera en que administramos la comunicación entre componentes distribuidos.

## Diapositiva 3. Demostración práctica de PUB/SUB

Para comprobar este funcionamiento, prepararemos una simulación utilizando un broker MQTT local mediante Eclipse Mosquitto.

El objetivo es representar un evento del SGRA sin necesidad de ejecutar toda la aplicación original.

Tenemos tres terminales. Las primeras dos representan consumidores diferentes: uno de notificaciones y otro de auditoría.

Ambos están suscritos al mismo tópico, `sgra/tutorias/creada`.

La tercera terminal funciona como publicador. Desde ella enviamos el mensaje `Solicitud 42 creada`.

Cuando realizamos la publicación, los dos suscriptores reciben el mismo mensaje, porque ambos manifestaron interés en ese tópico.

Lo importante no es únicamente observar el mensaje en las terminales, sino comprender lo que acaba de ocurrir: **un publicador ha comunicado un evento a dos consumidores independientes mediante un intermediario**.

Si agregáramos un tercer suscriptor, podríamos incorporarlo sin modificar el comando del publicador.

Esta prueba demuestra el principio de distribución de mensajes, aunque todavía no representa una integración real con el backend del SGRA.

## Diapositiva 4. Evolución del SGRA hacia una arquitectura orientada a eventos

Finalmente, podemos relacionar lo aprendido con una propuesta de evolución arquitectónica para nuestro proyecto.

No buscamos reemplazar Angular, Spring Boot ni PostgreSQL. Estos componentes continuarían cumpliendo sus responsabilidades actuales.

La propuesta consiste en incorporar un broker de mensajería para distribuir determinados eventos de negocio.

Por ejemplo, cuando una solicitud de tutoría se registra correctamente en PostgreSQL, el sistema podría generar el evento `SolicitudTutoriaCreada`.

Ese evento permitiría que componentes independientes ejecuten tareas complementarias, como enviar notificaciones, registrar auditorías o actualizar indicadores.

Esto nos proporcionaría mayor desacoplamiento y facilitaría la incorporación de nuevas funcionalidades.

Pero existe un problema que debemos considerar: ¿qué sucede si la solicitud se guarda correctamente en PostgreSQL, pero el broker no recibe el evento?

En ese caso, podríamos tener una solicitud registrada sin que los componentes interesados reciban la notificación.

Una alternativa de diseño sería utilizar el patrón **Transactional Outbox**, almacenando el evento pendiente dentro de la misma transacción de base de datos y publicándolo posteriormente mediante un proceso independiente.

De esta manera, podríamos mejorar la confiabilidad de la comunicación, aunque también tendríamos que gestionar reintentos y posibles mensajes duplicados.

Por eso, nuestra propuesta no consiste simplemente en agregar un broker, sino en evaluar cómo integrar mensajería asíncrona manteniendo la consistencia y confiabilidad del sistema.

A partir de estos fundamentos, podemos estudiar tecnologías concretas que implementan mecanismos de mensajería. Una de ellas es MQTT, que será explicada a continuación por mi compañero Carlos.
