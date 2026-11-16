# SGRA + PUB/SUB — estructura inicial de exposición

Material **inicial** de trabajo colaborativo: portada y sección de Mario (4 diapositivas),
con espacios reservados para las secciones de Carlos, Nixon y John. Las afirmaciones sobre
la integración del broker al SGRA son **propuestas**, no funciones existentes.

## Compilación

Compilador: **pdfLaTeX** con Biber. Archivo principal: `main.tex`.

```sh
pdflatex -interaction=nonstopmode main.tex
biber main
pdflatex -interaction=nonstopmode main.tex
pdflatex -interaction=nonstopmode main.tex
```

Dependencias de LaTeX: `beamer`, `tikz/pgf`, `biblatex`, `biber`, `babel`, `lmodern`,
`booktabs`, `hyperref`, `xurl`, fuentes estándar TeX Live.

Para Overleaf: seleccionar **pdfLaTeX**, cargar el proyecto conservando las carpetas,
y usar Biber como procesador bibliográfico.

## Archivos

- `main.tex`: diseño común, portada, carga de secciones, bibliografía.
- `secciones/mario.tex`: introducción, patrón PUB/SUB, demo y evolución SGRA.
- `secciones/carlos.tex`: reservado para MQTT.
- `secciones/nixon.tex`: reservado para JMS.
- `secciones/john.tex`: reservado para AMQP.
- `referencias.bib`: seis artículos de revistas (2022-2024) con DOI contrastado en registros de editoriales o instituciones, más documentación técnica oficial y dos repositorios públicos de código fuente del SGRA. Las páginas sin DOI NO cuentan como artículos de revista.
- `demo/`: un ejercicio local reproducible con Docker/Mosquitto.

## Integridad académica

- Los integrantes deben revisar el contenido, consultar las fuentes, crear sus propias
  evidencias y dejar commits auténticos. No copiar el material sin entenderlo.
- Registrar en las **notas finales** el uso de IA, herramientas/versiones, finalidad
  y forma de verificación, conforme al encuadre de la asignatura.
- El paquete es un borrador de presentación: aún faltan el análisis completo requerido
  por la guía, las notas de 2-4 páginas, las evidencias y el ensayo.
- No se ha probado la ejecución de la demostración Docker en el equipo de los estudiantes.

## Citas sobre SGRA

El frontend y backend del SGRA se citan directamente por sus repositorios públicos en GitHub. No se incorpora ni cita ninguna descripción interna aportada como contexto. Los repositorios respaldan tecnologías y código disponibles; la arquitectura de mensajería es una propuesta, no una característica demostrada como implementada.
