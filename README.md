# Mensajería PUB/SUB: MQTT, JMS y AMQP

Repositorio de trabajo del equipo para la exposición del primer
corte de Aplicaciones Distribuidas (ISR-701).

La presentación estudia la mensajería Publish/Subscribe y plantea
su posible aplicación al SGRA — Sistema de Gestión de Refuerzo
Académico.

La integración de mensajería con el SGRA se presenta como una
propuesta de mejora. La demostración MQTT utiliza datos ficticios
y se ejecuta de manera independiente del sistema real.

## Integrantes y distribución

| Integrante | Tema | Material incorporado |
|---|---|---|
| Mario Zambrano | Fundamentos de PUB/SUB | Sección introductoria y diagramas de arquitectura. |
| Carlos Manotoa | Protocolo MQTT | Tres diapositivas, diagramas TikZ, notas de exposición, referencias y evidencias de la práctica. |
| Nixon Piguave | JMS / Jakarta Messaging | Archivo de sección reservado. |
| John Silva | AMQP y análisis comparativo | Archivo de sección reservado. |

## Estructura del repositorio

```text
.
├── main.tex
├── notas.tex
├── notas.pdf
├── notas/
│   └── carlos.tex
├── referencias.bib
├── BITACORA.md
├── README.md
├── secciones/
│   ├── mario.tex
│   ├── carlos.tex
│   ├── nixon.tex
│   └── john.tex
├── demo/
│   ├── docker-compose.yml
│   ├── mosquitto.conf
│   ├── PRACTICA.md
│   └── PRACTICA-CLAB.md
└── evidencias/
    ├── Mario Practica/
    └── carlos-mqtt/
        ├── evidencia practica/
        │   └── capturas de CLAB y registro de Mosquitto
        ├── practica-clab.log.txt
        └── evidencia lectura/
            ├── fuentes en PDF
            └── imágenes con fragmentos subrayados
```

- `main.tex`: define el diseño común, la portada, el orden de
  las secciones y la bibliografía.
- `secciones/`: contiene las diapositivas y notas Beamer de
  cada integrante.
- `referencias.bib`: reúne las referencias compartidas.
- `demo/`: contiene la configuración de la demostración local.
- `evidencias/`: almacena las capturas y los materiales de apoyo.
- `BITACORA.md`: registra las actividades, dificultades,
  soluciones y uso de herramientas.

Los diagramas de la sección MQTT están dibujados en TikZ y no
dependen de imágenes externas.

## Compilación de las diapositivas

- **Compilador:** pdfLaTeX.
- **Archivo principal:** `main.tex`.
- **Procesador bibliográfico:** Biber.
- **Formato bibliográfico:** IEEE mediante BibLaTeX.

Desde la raíz del repositorio, ejecutar:

```bash
pdflatex -interaction=nonstopmode -halt-on-error main.tex
biber main
pdflatex -interaction=nonstopmode -halt-on-error main.tex
pdflatex -interaction=nonstopmode -halt-on-error main.tex
```

El resultado se genera como `main.pdf`.

### Dependencias de LaTeX

Se requiere una distribución de LaTeX con los siguientes
paquetes y componentes:

- `beamer`.
- `tikz/pgf`.
- `biblatex` y el estilo `biblatex-ieee`.
- `biber`.
- `babel` con soporte para español.
- `lmodern`.
- `booktabs`.
- `csquotes`.
- `hyperref`.
- `xurl`.

### Uso en Overleaf

1. Cargar el proyecto conservando su estructura de carpetas.
2. Seleccionar `main.tex` como documento principal.
3. Utilizar pdfLaTeX y Biber.
4. Recompilar desde cero si las referencias no se actualizan.

## Compilación de las notas grupales

El borrador de [las notas](notas.pdf) se compila desde la raíz del repositorio.
Usa pdfLaTeX, Biber y la bibliografía compartida. Requiere `babel` con
español, `lmodern`, `geometry`, `graphicx`, `booktabs`, `tabularx`,
`csquotes`, `biblatex-ieee`, `xurl` y `hyperref`.

```bash
pdflatex -interaction=nonstopmode -halt-on-error notas.tex
biber notas
pdflatex -interaction=nonstopmode -halt-on-error notas.tex
pdflatex -interaction=nonstopmode -halt-on-error notas.tex
```

Carlos está integrado desde `notas/carlos.tex`. Mario, Nixon y John pueden
agregar `notas/mario.tex`, `notas/nixon.tex` y `notas/john.tex`. Cada
fragmento empieza con su encabezado `\subsection` y usa las citas de
`referencias.bib`. El documento carga cada fragmento cuando existe.

Antes de publicar la versión final, completar las declaraciones de IA y
guardar la foto original del bosquejo como `evidencias/bosquejo-inicial.jpg`.
Al recompilar, comprobar que el anexo muestra los nombres y la fecha y que
el PDF completo tiene entre 2 y 4 páginas.

## Demostración MQTT

La [demo MQTT de Carlos](demo/PRACTICA-CLAB.md) simula el evento
`ReporteFallaResuelto` en el tópico `clab/fallas/resuelta`. Mosquitto
distribuye el mensaje a dos clientes MQTTX con QoS 1. La integración
con CLAB se presenta como una propuesta basada en el caso de uso
CU-04 del informe del proyecto.

## Fuentes y alcance de la propuesta

Los repositorios públicos de frontend y backend del SGRA
se utilizan como referencias del código y las tecnologías
documentadas por el equipo.

La arquitectura de mensajería propuesta no se presenta como
una característica ya implementada en el SGRA. Los resultados
de la práctica MQTT corresponden exclusivamente al entorno
local de demostración.

Las referencias bibliográficas se encuentran en
`referencias.bib` y se incorporan al PDF mediante las citas
de las diapositivas.

## Uso de herramientas de IA

El apoyo de Codex se documenta en `BITACORA.md` y en la
documentación de la práctica. Incluye búsqueda y contraste
de información, propuestas de redacción y código, resolución
de problemas, preparación del entorno y automatización de
la demostración y sus capturas.

Este apoyo no sustituye la lectura de las fuentes ni el
dominio que debe demostrar cada integrante durante la
exposición.

**Declaración de Carlos Manotoa.** Se utilizó Codex como apoyo para la búsqueda y el filtrado de información, para la transformación rápida de contenido entre formatos (de txt a tex o md) y como apoyo en la resolución de los problemas encontrados. No obstante, la redacción, la revisión final y la documentación fueron realizadas y revisadas por el estudiante.

## Material grupal pendiente de integración

- Completar las secciones de JMS y AMQP.
- Integrar los apartados de análisis exigidos por la guía.
- Completar las notas grupales con los guiones y declaraciones de IA
  pendientes y la fotografía original del bosquejo.
- Añadir el enlace accesible al video del ensayo grupal.

## Video del ensayo

**Enlace:** pendiente de incorporación.
