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
- `referencias.bib`
- `demo/`: un ejercicio local reproducible con Docker/Mosquitto.

## Citas sobre SGRA

El frontend y backend del SGRA se citan directamente por sus repositorios públicos en GitHub. No se incorpora ni cita ninguna descripción interna aportada como contexto. Los repositorios respaldan tecnologías y código disponibles; la arquitectura de mensajería es una propuesta, no una característica demostrada como implementada.
