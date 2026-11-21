# Bitácora de trabajo — GA-SUM-02 / EXP-1

**Asignatura:** Aplicaciones Distribuidas (ISR-701)  
**Tema:** Mensajería Publish/Subscribe (PUB/SUB): MQTT, JMS y AMQP  
**Proyecto de aplicación:** SGRA — Sistema de Gestión de Refuerzo Académico  
**Equipo:** Zambrano Parraga Luis Mario; Manotoa Alava Carlos Alexander; Piguave Montaño Nixon Antonio; Silva Triviño John Jairo.  

---

## 15 de noviembre de 2026

**Tipo de actividad:** Preparación individual de la exposición (registro inicial).  
**Integrante:** Zambrano Parraga Luis Mario.  
**Tiempo dedicado:** 4 horas y 27 minutos

### Actividades realizadas

- Revisé las indicaciones de EXP-1 en el SGA y las reglas del encuadre académico sobre estructura, fuentes, evidencias, repositorio y exposición.
- Definí el SGRA como proyecto de aplicación para explicar PUB/SUB, conforme a la aclaración verbal del docente; se distinguió el funcionamiento existente de una futura propuesta de mensajería.
- Organicé una distribución preliminar de contenidos entre los cuatro integrantes: fundamentos de PUB/SUB, MQTT, JMS y AMQP.
- Trabajé en un **borrador** de presentación compartida en LaTeX Beamer, con archivos por integrante (`secciones/mario.tex`, `carlos.tex`, `nixon.tex` y `john.tex`) y diagramas TikZ.
- Revisé el diseño visual del borrador y solicité ajustes a una paleta verde oscuro y referencias en negro.
- Reemplacé la propuesta de citar una descripción interna no oficial del SGRA por sus repositorios públicos de frontend y backend como fuentes del código del proyecto.
- Planifiqué una demostración local con Mosquitto para publicar un evento simulado de tutoría y recibirlo en dos suscriptores.

### Dificultades encontradas

- La guía escrita del SGA plantea el análisis de un sistema distribuido real, mientras que el docente indicó aplicar los temas a los proyectos de fin de curso.
- El SGRA lleva tiempo sin mantenerse; no está comprobado que pueda ejecutarse actualmente en un entorno local sin ajustes.

### Soluciones aplicadas

- Se adoptó la secuencia explicativa indicada por el docente: **teoría → ejercicio sencillo → aplicación al SGRA**.
- Se planteó una práctica aislada de MQTT que no dependa de recuperar el despliegue del SGRA.
- Se ajustó el borrador Beamer y se identificaron los repositorios del SGRA como fuentes apropiadas para documentar su código.

### Resultados obtenidos

- Esquema inicial de la exposición y responsabilidades por integrante.
- Borrador de la sección introductoria de PUB/SUB y propuesta arquitectónica para el SGRA.
- Estructura de proyecto Beamer con archivos de sección y bibliografía compartida.

### Evidencias

- Archivos de trabajo: `main.tex`, `secciones/mario.tex`, `referencias.bib` y PDF provisional.


///////////////////////

## 15 de noviembre de 2026 — Preparación de la sección MQTT

**Tipo de actividad:** Preparación individual con apoyo de herramientas de IA.  
**Integrante:** Manotoa Alava Carlos Alexander.  
**Tema asignado:** Protocolo MQTT y posible aplicación al SGRA.

### Actividades realizadas

- Se seleccionaron tres artículos científicos sobre MQTT, publicados entre 2022 y 2026, con DOI y acceso abierto. Con apoyo de Codex se contrastaron sus datos bibliográficos y se prepararon las entradas para `referencias.bib`.
- Se preparó el contenido sobre arquitectura MQTT, tópicos y niveles QoS 0, 1 y 2, distinguiendo las garantías del protocolo del procesamiento de una operación de negocio.
- Se elaboraron tres diapositivas: arquitectura y QoS; demostración con Mosquitto y MQTTX; y propuesta de aplicación al SGRA.
- Se revisó el primer diseño y se redujo el texto visible. Los gráficos se implementaron en TikZ dentro de `secciones/carlos.tex`, conservando la paleta y los estilos del equipo.
- Se prepararon notas de exposición para explicar los gráficos y los límites de la propuesta.
- Se configuró una copia independiente de la práctica, fuera del repositorio, para realizar la demostración sin modificar los archivos originales durante las pruebas.
- Se reutilizó Docker Engine y Docker Compose, se habilitó el acceso del usuario al servicio y se instaló MQTTX Desktop.
- Se ejecutó Mosquitto en Docker y se configuraron tres conexiones MQTT 5.0: `pub-sgra`, `sub-notificaciones` y `sub-auditoria`.
- Se publicaron datos ficticios en el tópico `sgra/tutorias/creada`, con QoS 1 y Retain desactivado.
- Se verificó la recepción del evento en ambos suscriptores y se guardaron capturas de configuración, publicación, recepción y registro del broker.
- Se incorporaron las diapositivas, referencias y capturas al repositorio. También se redactó el README de las evidencias, pendiente de commit al iniciar esta revisión.
- Se comparó el material con la guía de la exposición y se preparó una actualización de las notas para reflejar que la demostración ya fue ejecutada.

### Actualización — Preparación de evidencias de lectura

- Se reunieron los PDF de las tres fuentes científicas utilizadas en la sección MQTT.
- Con apoyo de Codex se verifico la información de las fuentes:
- Arquitectura, tópicos y niveles QoS: Shahri et al., página 2.
- Alcance de las confirmaciones al publicador: Im y Lim, página 2.
- Uso observado de QoS y limitaciones del estudio: Innamorati et al., página 8.
- Se agregaron los PDF de las fuentes.

### Dificultades encontradas

- El primer diseño tenía demasiado texto para utilizarlo como apoyo visual durante la exposición.
- La primera versión gráfica dependía de un archivo de imágenes externo.
- Docker estaba instalado, pero el usuario no tenía acceso al servicio.
- Las notas conservaban una descripción de la demostración como actividad pendiente.
- El material necesitaba distinguir con claridad la práctica local de una integración real con el SGRA.

### Soluciones aplicadas

- Se trasladó la explicación extensa a las notas y se utilizaron nombres, datos, gráficos y secuencias de paquetes en las diapositivas.
- Se preparó una versión con ilustraciones vectoriales TikZ, sin imágenes externas.
- Se añadió el usuario al grupo `docker` y se comprobó el acceso al servicio.
- Se redactó la actualización de las notas a partir de los resultados observados.
- Se identificó la práctica como una simulación con datos ficticios y la integración con el SGRA como una propuesta de mejora.

### Resultado de la práctica

La ejecución se realizó el 15 de noviembre de 2026, aproximadamente a las 21:49:58.

Herramientas utilizadas:

- Docker Engine 29.9.3.
- Docker Compose 5.7.0.
- Eclipse Mosquitto 2.1.2.
- MQTTX Desktop 1.13.3.
- MQTT 5.0.

El cliente `pub-sgra` publicó el evento `evt-001`, de tipo `SolicitudTutoriaCreada`, asociado a la solicitud ficticia número `42`.

MQTTX registró una publicación y una recepción en cada suscriptor. El registro de Mosquitto mostró los envíos PUBLISH y las confirmaciones PUBACK correspondientes.

La prueba comprobó la distribución del evento durante esa ejecución. No se realizaron pruebas de recuperación ante fallos, generación de duplicados ni mediciones de latencia.

### Evidencias y archivos relacionados

- `secciones/carlos.tex`: diapositivas, gráficos TikZ y notas de exposición.
- `referencias.bib`: referencias científicas utilizadas.
- `main.pdf`: presentación compilada.
- `evidencias/carlos-mqtt/`: capturas de la práctica y documentación de las evidencias.
- Commit inicial de la sección: `953e53ebf40083ada8ecc8c4d7ac993f3eb16106`, con el mensaje «Carlos: Diapositivas + ejercicio (pendiente de revisión)».

### Uso de IA y verificación

Se utilizó Codex como herramienta de apoyo para buscar y filtrar información, contrastar datos bibliográficos, resolver problemas encontrados durante la preparación y orientar el trabajo paso a paso.

También se empleó para adaptar el contenido trabajado con el estudiante a los formatos `.bib` y `.tex`, proponer borradores de redacción y código TikZ, y realizar ajustes a partir de las revisiones e indicaciones del estudiante.

En la práctica, Codex apoyó la configuración inicial del entorno. La verificación incluyó la compilación del proyecto, la revisión del PDF y la comprobación de los mensajes y registros reales de MQTTX y Mosquitto.

Este apoyo no sustituye la lectura personal de las fuentes ni el dominio del contenido que debe demostrar el estudiante durante la exposición.

/////////////////////////////////////////////
---

## 15 de noviembre de 2026 — Ejecución de la práctica PUB/SUB con MQTT

**Integrante:** Zambrano Parraga Luis Mario.

**Tema:** Demostración del patrón Publish/Subscribe aplicado a un evento simulado del SGRA.

### Actividades realizadas

- Se realizó una práctica local para demostrar el funcionamiento del patrón de comunicación Publish/Subscribe utilizando Eclipse Mosquitto como broker MQTT.
- Se utilizó Docker para ejecutar el broker de mensajería en un entorno local.
- Se configuraron dos clientes suscriptores, representando los módulos de notificaciones y auditoría del SGRA.
- Se estableció el tópico `sgra/tutorias/creada` como canal de comunicación para los eventos simulados.
- Se utilizó un tercer cliente como publicador para representar la generación de una solicitud de tutoría.
- Se ejecutaron los comandos de publicación y suscripción desde terminales independientes.
- Se comprobó la recepción del mensaje publicado en ambos suscriptores.
- Se tomaron capturas de pantalla de la ejecución para documentar el funcionamiento de la comunicación PUB/SUB.
- Se prepararon las evidencias obtenidas para incorporarlas al repositorio de GitHub.

### Dificultades encontradas

- El SGRA original no dispone de una integración PUB/SUB documentada y lleva tiempo sin actualizarse, por lo que ejecutar toda la aplicación para realizar la demostración implicaría trabajo adicional.
- Era necesario comprobar el funcionamiento del patrón sin depender del backend original ni de los servicios desplegados en Azure.

### Soluciones aplicadas

- Se utilizó un entorno de pruebas independiente mediante Docker y Mosquitto.
- Se simuló el evento de creación de una tutoría utilizando datos ficticios.
- Se representaron los módulos de notificaciones y auditoría mediante clientes suscriptores independientes.
- Se documentó la práctica como una demostración del patrón PUB/SUB y no como una funcionalidad ya implementada en el SGRA.

### Resultados obtenidos

- Se ejecutó una demostración local del patrón PUB/SUB mediante MQTT.
- Se comprobó que un mensaje publicado en el tópico correspondiente podía ser recibido por dos suscriptores independientes.
- Se evidenció el desacoplamiento entre el publicador y los consumidores, ya que el primero no necesitó establecer conexiones directas con cada suscriptor.
- Se obtuvieron capturas de pantalla como evidencia de la ejecución.
- La práctica permitió relacionar los fundamentos de PUB/SUB con una posible evolución de la arquitectura del SGRA.

**Alcance de la prueba:** se verificó la publicación y recepción de mensajes en el entorno local. Esta demostración no constituye una integración funcional con el SGRA original ni demuestra por sí sola tolerancia a fallos o garantías de entrega ante desconexiones.

### Evidencias

- Capturas de pantalla del broker MQTT en ejecución.
- Capturas de las terminales de publicación y suscripción.
- Registros visuales de la recepción de mensajes en ambos consumidores.
- Archivos de configuración y comandos utilizados en la práctica.

**Ubicación de las evidencias:** `evidencias/Mario Practica/`.

### Tiempo dedicado
20 minutos.

---

## 20 de noviembre de 2026 — Documentación de la práctica PUB/SUB

**Tipo de actividad:** Documentación de evidencias y resultados de la práctica.

**Integrante:** Zambrano Parraga Luis Mario.

**Tema:** Demostración del patrón Publish/Subscribe mediante MQTT, aplicado a un evento simulado del SGRA.

### Actividades realizadas

- Revisé las capturas de la práctica PUB/SUB realizada anteriormente con Docker y Eclipse Mosquitto.
- Organicé la documentación de la prueba para explicar su objetivo, las herramientas utilizadas y el procedimiento de publicación y suscripción.
- Creé el archivo `README.md` dentro de la carpeta `evidencias/Mario Practica/`, donde se describe el funcionamiento de los clientes y el broker MQTT.
- Documenté los resultados observados durante la ejecución, en la que dos suscriptores recibieron los mensajes enviados por un publicador.
- Identifiqué las limitaciones de la práctica y aclaré que se trata de una simulación, no de una integración funcional con el backend del SGRA.

### Dificultades encontradas

- Las capturas evidenciaban la ejecución de la práctica, pero faltaba un documento que explicara el procedimiento y los resultados obtenidos.
- Era necesario diferenciar la simulación local de PUB/SUB de una implementación real dentro del SGRA.

### Soluciones aplicadas

- Se elaboró un README específico para facilitar la comprensión y revisión de las evidencias.
- Se documentaron el alcance y las limitaciones de la prueba, evitando atribuir al SGRA funcionalidades que todavía no están implementadas.

### Resultados obtenidos

- Documentación de la práctica PUB/SUB incorporada al repositorio.
- Evidencias de ejecución acompañadas de una explicación técnica.
- Material preparado para respaldar la demostración en clase.
- Se identificó como siguiente etapa la integración de un publicador de eventos con el backend del SGRA.

### Evidencias y archivos relacionados

- `evidencias/Mario Practica/README.md`: documentación de la práctica.
- `evidencias/Mario Practica/`: capturas originales de publicación y suscripción.

### Uso de inteligencia artificial

Se utilizó ChatGPT (GPT-6) como herramienta de apoyo para estructurar y redactar la documentación de la práctica. La información sobre su ejecución y resultados se contrastó con las evidencias obtenidas previamente.

### Tiempo dedicado
45 minutos.
