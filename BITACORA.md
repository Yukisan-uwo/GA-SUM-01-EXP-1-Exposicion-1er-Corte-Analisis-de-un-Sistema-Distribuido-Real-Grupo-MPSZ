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

### Registro del apoyo de IA y verificación

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


---

## Registros individuales de Nixon Piguave — JMS / Jakarta Messaging

**Asignatura:** Aplicaciones Distribuidas (ISR-701).  
**Integrante responsable:** Piguave Montaño Nixon Antonio.  
**Alcance:** aporte individual sobre JMS; inicialmente aplicado al SGRA y posteriormente orientado al RTV. El cambio corresponde únicamente a mi parte.  
**Fecha de elaboración de este registro:** 21 de noviembre de 2026.  
**Base del registro:** commits publicados, archivos entregados y ejecuciones documentadas. Las entradas anteriores se reconstruyen retrospectivamente; no representan anotaciones escritas en aquellas fechas. No se registraron tiempos de dedicación ni asistentes adicionales.

## 16 de noviembre de 2026 — Primer aporte: JMS aplicado al SGRA

**Modalidad:** trabajo individual con apoyo del asistente.  
**Commit:** `0c49f60` — «Diapositivas JMS + práctica Java y evidencias de ejecución».

### Actividades y resultados

- Incorporé tres diapositivas sobre la API JMS, el funcionamiento de productor, tópico y suscriptores, y una propuesta de aplicación al backend del SGRA.
- Trabajé con diagramas TikZ dentro de la sección Beamer y referencias en la bibliografía compartida.
- Incorporé una demostración Java independiente con Apache Artemis, configuración Docker Compose y dependencias Maven, preparada con apoyo del asistente.
- Ejecuté la práctica con dos suscriptores, notificaciones y auditoría, y un productor del evento ficticio `SolicitudTutoriaCreada`.
- Aporté seis capturas del entorno, las suscripciones, la publicación y las recepciones. Organicé los archivos para subirlos al repositorio.

### Problemas y soluciones

- Al cargar los archivos en Overleaf, la compilación buscaba `secciones/mario.tex`, pero los archivos estaban fuera de esa carpeta. Organicé las rutas para que coincidieran con las instrucciones `input` del documento principal.
- La demostración no debía presentarse como una función ya implementada en el SGRA. Se documentó como una práctica local con datos ficticios.
- Para comprobar la difusión, comparé el cuerpo y el identificador JMS del mensaje publicado con los recibidos por ambos suscriptores.

**Archivos vinculados:** `secciones/nixon.tex`, `referencias.bib`, `demo/jms/` y `evidencias/nixon-jms/practica/`.

## 20 de noviembre de 2026 — Adaptación del análisis al RTV

**Modalidad:** trabajo individual   
**Commit:** `a1a8f9d` — «Adapto la sección JMS al RTV y defino el evento InspeccionIniciada».

### Actividades y resultados

- Cambié el proyecto de aplicación de mi aporte: del SGRA al Sistema de Revisión Técnica Vehicular del Municipio de Valencia.
- Organicé mi material en la carpeta `Nixon Piguave/`, siguiendo la separación de aportes individuales utilizada en el repositorio.
- Documenté el flujo del prototipo: Inspecciones solicita autorización a Turnos y Pagos mediante REST antes de registrar el inicio.
- Definí el evento propuesto `InspeccionIniciada`, el tópico `rtv.inspecciones.iniciada` y dos suscripciones independientes para auditoría e indicadores.
- Registré los datos del evento y las decisiones pendientes sobre publicación después de guardar, duplicados y desconexiones.

### Problemas y soluciones

- Las evidencias anteriores correspondían a tutorías, por lo que no demostraban una ejecución del RTV. Las conservé identificadas como SGRA y planifiqué una práctica nueva con datos ficticios de inspección.
- Separé la autorización REST existente de la difusión JMS propuesta para evitar atribuir al prototipo una integración que todavía no tiene.

**Archivos incorporados en este commit:** `Nixon Piguave/README.md`, `Nixon Piguave/analisis-rtv-jms.md` y `Nixon Piguave/fuentes-nixon.bib`. Aunque el mensaje del commit menciona la sección, este commit no modificó `secciones/nixon.tex`; la integración de las diapositivas se publicó el 21 de noviembre.

## 21 de noviembre de 2026 — Práctica RTV, evidencias e integración

**Modalidad:** trabajo individual con apoyo de codex 
**Commit:** `042d678` — «práctica JMS del RTV y evidencias de ejecución».

### Actividades y resultados

- Incorporé la nueva práctica Java del RTV con Jakarta Messaging y Apache Artemis 2.57.0, preparada con apoyo de codex.
- Ejecuté el flujo en tres terminales: A para auditoría, B para indicadores y C para el broker y el productor.
- Inicié ambos suscriptores antes de publicar `evt-rtv-001`, de tipo `InspeccionIniciada`, para el turno ficticio 42.
- Comprobé que productor y ambos receptores mostraban el mismo cuerpo y `JMSMessageID`: `ID:698a6d8e-e575-11f1-b4d4-e626e217634f`.
- Tomé y aporté seis capturas originales. 
- Incorporé los registros de una comprobación previa ejecutada por el asistente, identificados como una ejecución diferente de la que aparece en mis capturas.
- Actualicé las tres diapositivas para orientar mi explicación al RTV e integré el PDF compilado. Se retiró de la bibliografía la entrada del repositorio RTV a petición mía.
- Revisé el paquete antes de subirlo: mis archivos quedaron en `Nixon Piguave/` y `secciones/nixon.tex`; se actualizó también `main.pdf`.

### Problemas y soluciones

- Los identificadores de los registros previos y de mis capturas eran diferentes porque correspondían a dos ejecuciones. Se explicó esa diferencia en `evidencias/practica/LEEME.txt`.

**Archivos vinculados:** `Nixon Piguave/practica-jms-rtv/`, `Nixon Piguave/evidencias/practica/`, documentación individual, `secciones/nixon.tex` y `main.pdf`.

### Preparación posterior al commit, durante la misma fecha

- Preparé con apoyo del asistente un borrador de guion de aproximadamente tres minutos, con explicación conceptual, demostración y aplicación al RTV.
- Organicé el procedimiento para dejar el broker y ambos suscriptores listos antes de exponer, de modo que durante la presentación solo sea necesario publicar y mostrar las recepciones.
- Reconstruí esta bitácora a partir del historial y las evidencias. Estas actividades posteriores no se presentan como parte del commit `042d678`.

## Alcance de las pruebas y uso de IA

La práctica demuestra la distribución de un evento a dos suscripciones no durables activas. No conecta los servicios reales del RTV, no guarda auditorías ni indicadores y no demuestra recuperación después de una desconexión.

Utilicé  Codex para organizar el trabajo, consultar documentación, preparar borradores de código Java, diagramas TikZ, y estructura de diapositivas en latex, y tambien para un borrador de un guion para la exposicion.

Quedan pendientes la revisión y el ensayo cronometrado del guion, la integración de mi explicación en las notas LaTeX del equipo y la preparación de respuestas.

---

## 21 de noviembre de 2026 — Adaptación de la sección MQTT a CLAB

**Sección:** Carlos Manotoa.

- Se adaptaron los diagramas y las notas de MQTT al caso CU-04 de CLAB,
  en el que un administrador marca un reporte de falla como resuelto.
- Se preparó una práctica reproducible con el evento ficticio
  `ReporteFallaResuelto` y el tópico `clab/fallas/resuelta`.
- Se comprobó la publicación y la recepción del mismo JSON en dos
  suscriptores con MQTTX CLI 1.13.3, Mosquitto 2.0.22 y QoS 1. Se usó
  un broker local porque el servicio de Docker estaba detenido.
- Se recompiló la presentación con pdfLaTeX y Biber.
- Codex apoyó la revisión del informe de CLAB y la
  adaptación de las diapositivas.

**Material:** [guía de la demo](demo/PRACTICA-CLAB.md) y
[registro de la prueba](evidencias/carlos-mqtt/practica-clab.log.txt).

**Verificación adicional con MQTTX Desktop.** Se configuraron tres
conexiones independientes con MQTT 5.0 y se publicó el evento ficticio
con QoS 1. Mosquitto registró la entrega y el PUBACK de ambos suscriptores.
La [guía de la demo](demo/PRACTICA-CLAB.md#evidencia) enlaza las capturas
obtenidas de la interfaz y el registro correspondiente a esta ejecución.

### Preparación de las notas de exposición de Carlos

**Integrante:** Carlos Manotoa.

- Se revisaron la arquitectura y el despliegue documentados en el informe
  de CLAB, el caso de uso CU-04 y los resultados de las pruebas funcionales.
- Se incorporó el defecto detectado con Postman en los avisos por bloqueos
  de eventos institucionales. El informe registra la corrección en
  `BloqueoService` durante el mismo sprint.
- Se explicó el alcance del análisis CAP y PACELC. El diagrama muestra un
  servidor PostgreSQL y el informe deja abierta la respuesta ante
  particiones y la conmutación automática. Se documentó la mensajería con
  MQTT como una propuesta para el flujo de resolución de fallas.
- Se preparó el [fragmento LaTeX de Carlos](notas/carlos.tex) y se comprobó
  su compilación con pdfLaTeX, Biber y la bibliografía del equipo.

### Integración en el documento grupal

- Se incorporó el guion de Carlos en [notas.tex](notas.tex) y se verificó
  la compilación del [borrador de dos páginas](notas.pdf) con pdfLaTeX,
  Biber y referencias IEEE.
- Se incluyeron la tabla de aportes, la declaración de uso de IA de Carlos
  y el anexo para la fotografía del bosquejo. Se prepararon los puntos
  de entrada para los guiones y declaraciones de los demás integrantes.
- Se registraron los comandos de compilación y las instrucciones de
  integración en el README.

### Declaración de uso de IA de Carlos Manotoa

Se utilizó Codex como apoyo para la búsqueda y el filtrado de información, para la transformación rápida de contenido entre formatos (de txt a tex o md) y como apoyo en la resolución de los problemas encontrados. No obstante, la redacción, la revisión final y la documentación fueron realizadas y revisadas por el estudiante.

**Pendiente:** integrar los guiones y declaraciones restantes, incorporar
la fotografía original del bosquejo y cronometrar el ensayo grupal.


---

## 21 de noviembre de 2026 — Integración y pruebas PUB/SUB en el SGRA

**Integrante:** Zambrano Parraga Luis Mario.  
**Tiempo dedicado:** 3 horas y 54 minutos.

### Actividades realizadas
- Preparé el backend del SGRA en IntelliJ IDEA y verifiqué su compilación con Maven.
- Incorporé Eclipse Paho para publicar eventos MQTT desde Java mediante Mosquitto.
- Integré experimentalmente la publicación del evento `SolicitudTutoriaCreada` en el servicio de creación de solicitudes.
- Realicé pruebas de publicación, recepción, caída y recuperación del broker, así como de eventos duplicados.
- Ejecuté pruebas automatizadas con JUnit y Mockito, simulando la persistencia para no utilizar la base de datos real.
- Organicé las capturas y la documentación de los resultados en el repositorio.

### Dificultades encontradas
- El backend no tenía implementada la publicación de eventos MQTT.
- Inicialmente coloqué la clase de pruebas en `src/main/java`, provocando errores con las dependencias JUnit y Mockito.
- Al detener Mosquitto, Java no pudo establecer conexión con el broker.

### Soluciones aplicadas
- Incorporé un publicador MQTT independiente y configurable.
- Moví las pruebas a `src/test/java` y comprobé nuevamente la compilación.
- Reinicié Mosquitto y verifiqué que podían realizarse nuevas publicaciones.
- Utilicé Mockito para probar el flujo del servicio sin depender de PostgreSQL ni de correos reales.

### Resultados obtenidos
- Se comprobó la recepción del mismo evento por dos suscriptores independientes.
- Se observaron las limitaciones ante desconexiones y publicaciones duplicadas.
- **Tres pruebas unitarias y una prueba de integración servicio–broker finalizaron correctamente**, sin fallos.
- La integración es experimental: todavía no se ha probado la persistencia real en PostgreSQL.

### Evidencias
- `evidencias/Mario Practica/practica-grande-SGRA/`
- Capturas de IntelliJ, Maven, Docker y Mosquitto.
- Documentación del procedimiento y resultados de las pruebas.

### Uso de IA
Utilicé ChatGPT como apoyo para la implementación, solución de errores y documentación. Comprobé el funcionamiento mediante las pruebas ejecutadas en mi equipo.

---

## 21 de noviembre de 2026 — Integración de notas de exposición en LaTeX

**Integrante:** Zambrano Parraga Luis Mario.

### Actividades realizadas
- Preparé mi apartado para las notas grupales de la exposición, abordando el patrón PUB/SUB y su aplicación experimental al SGRA.
- Resumí la arquitectura, las pruebas realizadas y las limitaciones identificadas.
- Actualicé `notas.tex` para incorporar mi sección y añadí mi declaración de uso de inteligencia artificial.

### Dificultades y soluciones
Fue necesario adaptar el contenido técnico de la práctica al formato de las notas grupales, evitando repetir toda la información del informe de evidencias.

### Resultados
Se preparó mi contribución para las notas grupales, manteniendo la estructura LaTeX compartida y las referencias bibliográficas correspondientes.

### Uso de IA
Utilicé ChatGPT como apoyo para organizar el apartado, revisando su coherencia con las pruebas realizadas.

---
