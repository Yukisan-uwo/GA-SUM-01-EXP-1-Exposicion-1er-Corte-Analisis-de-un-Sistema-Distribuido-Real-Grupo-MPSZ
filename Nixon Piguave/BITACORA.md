# Bitácora de Nixon Piguave — JMS / Jakarta Messaging

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

**Modalidad:** trabajo individual.  
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

**Modalidad:** trabajo individual con apoyo de codex.  
**Commit:** `042d678` — «práctica JMS del RTV y evidencias de ejecución».

### Actividades y resultados

- Incorporé la nueva práctica Java del RTV con Jakarta Messaging y Apache Artemis 2.57.0, preparada con apoyo de codex.
- Ejecuté el flujo en tres terminales: A para auditoría, B para indicadores y C para el broker y el productor.
- Inicié ambos suscriptores antes de publicar `evt-rtv-001`, de tipo `InspeccionIniciada`, para el turno ficticio 42.
- Comprobé que productor y ambos receptores mostraban el mismo cuerpo y `JMSMessageID`: `ID:698a6d8e-e575-11f1-b4d4-e626e217634f`.
- Tomé y aporté seis capturas originales.
- Incorporé los registros de una comprobación previa ejecutada por el asistente, identificados como una ejecución diferente de la que aparece en mis capturas.
- Actualicé las tres diapositivas para orientar mi explicación al RTV e integré el PDF compilado.

### Problemas y soluciones

- Los identificadores de los registros previos y de mis capturas eran diferentes porque correspondían a dos ejecuciones. Se explicó esa diferencia en `evidencias/practica/LEEME.txt`.

**Archivos vinculados:** `Nixon Piguave/practica-jms-rtv/`, `Nixon Piguave/evidencias/practica/`, documentación individual, `secciones/nixon.tex` y `main.pdf`.

### Preparación posterior al commit, durante la misma fecha

- Preparé con apoyo de codex un borrador de guion de aproximadamente tres minutos, con explicación conceptual, demostración y aplicación al RTV.
- Organicé el procedimiento para dejar el broker y ambos suscriptores listos antes de exponer, de modo que durante la presentación solo sea necesario publicar y mostrar las recepciones.
- Reconstruí esta bitácora a partir del historial y las evidencias. Estas actividades posteriores no se presentan como parte del commit `042d678`.

## Alcance de las pruebas y uso de IA

La práctica demuestra la distribución de un evento a dos suscripciones no durables activas. No conecta los servicios reales del RTV, no guarda auditorías ni indicadores y no demuestra recuperación después de una desconexión.

Utilicé  Codex para organizar el trabajo, consultar documentación, preparar borradores de código Java, diagramas TikZ, y estructura de diapositivas en latex, y tambien para un borrador de un guion para la exposicion.

Quedan pendientes la revisión y el ensayo cronometrado del guion, la integración de mi explicación en las notas LaTeX del equipo y la preparación de respuestas.
## 21 de noviembre de 2026 — Preparación de notas y revisión de fuentes

**Modalidad:** preparación individual con apoyo de Codex. Registro posterior al commit de la práctica y al de la bitácora; no acredita ensayo ni lectura personal completados.

- Se preparó un fragmento LaTeX de mi explicación, con distribución prevista de 45 segundos de conceptos, 75 segundos de práctica y 60 segundos de aplicación al RTV.
- Se generó y revisó visualmente una vista previa individual de una página con bibliografía IEEE. Su incorporación al documento grupal sigue pendiente.
- El asistente comprobó las páginas oficiales de Jakarta y Spring y los datos editoriales de «Benchmarking Message Queues». El DOI 10.3390/telecom4020018 corresponde a los autores, título, revista, volumen y páginas registrados.
- Se descargó el PDF original del artículo y se identificaron las páginas 303 y 310 para orientar mi lectura
- Se mantuvo el alcance de la práctica: suscripciones no durables, datos ficticios y ausencia de integración con los servicios reales del RTV.

**Archivos:** `Nixon Piguave/notas/`, `Nixon Piguave/fuentes/`, bibliografía individual y README individual.


### Evidencia de lectura aportada durante la misma fecha

Aporté una captura de la página 303 del artículo «Benchmarking Message Queues», con la frase sobre latencia y throughput subrayada y una anotación relacionada con el RTV. La evidencia está en `Nixon Piguave/evidencias/lectura/`. El README aclara que la recepción del mismo evento se comprobó en la práctica, mientras que esas métricas no se midieron. 

## Declaración de uso de IA — actualización del 21/11/2026

Se utilizó OpenAI en Codex para organizar el aporte JMS, consultar fuentes, preparar borradores de código Java, configuración Docker, diagramas TikZ, estructurar el latex de las diapositivas y guion. Se ejecutó manualmente la práctica documentada en las seis capturas. Revisé personalmente las fuentes.
