# Bitácora de John Silva (EXP-1)

**Integrante:** Silva Triviño John Jairo
**Equipo:** MPSZ

## 18 de noviembre de 2026

**Tipo de actividad:** Preparación individual de la exposición.
**Tiempo dedicado:** 1:30 minutos.

### Actividades realizadas

- Revisé las indicaciones de EXP-1 y el reparto de contenidos del grupo (AMQP y análisis comparativo).
- Tras la clase del lunes, donde el docente pidió enfocar la exposición en el proyecto propio como si fuera distribuido, definí que mi parte se aplicará al SSDC.
- Analicé el código del SSDC para encontrar un flujo candidato a evento: la aprobación de una prepostulación (correo de credenciales con `@Async`, notificación interna y auditoría).
- Creé mi carpeta `John Jairo Silva Triviño/` con el análisis, la lista de fuentes y el plan de la práctica con RabbitMQ.
- Usé un asistente de IA (Claude) para apoyar el análisis del código y preparar borradores; yo reviso y verifico lo que entrego.

### Dificultades encontradas

- El SSDC no usa ningún broker; hay que presentarlo como propuesta futura y no como funcionalidad existente.
- El repositorio del grupo tenía el SGRA como caso de aplicación; el resto del grupo lo ajustará.

### Soluciones aplicadas

- Trabajé en una carpeta propia para no depender del resto de archivos mientras los demás integrantes ajustan sus secciones.

### Evidencias

- Archivos en `John Jairo Silva Triviño/`: `README.md`, `analisis-ssdc-eventos.md`, `fuentes-john.md` y `evidencias/`.

## 20 de noviembre de 2026

**Tipo de actividad:** Práctica con RabbitMQ.

### Actividades realizadas

- Levanté RabbitMQ con Docker y ejecuté mi práctica: un productor publica eventos de prepostulación y tres consumidores (correo, notificaciones, auditoría) los reciben según la clave de enrutamiento.
- Tomé 5 capturas del panel de RabbitMQ y de la terminal.

### Evidencias

- Capturas en la carpeta `evidencias/`.

## 21 de noviembre de 2026

**Tipo de actividad:** Integración de mi parte en el repositorio del grupo.

### Actividades realizadas

- Subí mis 4 diapositivas, las fuentes de mi parte en `referencias.bib` y mi guion de 3 minutos para las notas.
- Compilé el documento completo para comprobar que mi parte no rompe nada.

### Evidencias

- `secciones/john.tex` y `notas/john.tex` en el repositorio del grupo.
