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
│   └── PRACTICA.md
└── evidencias/
    ├── Mario Practica/
    └── carlos-mqtt/
        ├── evidencia ejercicio/
        │   ├── README.md
        │   └── capturas de la práctica
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

## Demostración MQTT

La práctica utiliza Mosquitto como broker y MQTTX Desktop
como cliente gráfico. Docker Engine con Docker Compose
permite ejecutar el broker; no es obligatorio instalar
Docker Desktop.

Configuración de la prueba documentada:

| Parámetro | Valor |
|---|---|
| Broker | `127.0.0.1:1883` |
| Versión del protocolo | MQTT 5.0 |
| Publicador | `pub-sgra` |
| Suscriptores | `sub-notificaciones` y `sub-auditoria` |
| Tópico | `sgra/tutorias/creada` |
| QoS de publicación y suscripciones | `1` |
| Retain de la publicación | Desactivado |
| Persistencia del broker | Desactivada |

Se publicó el evento `SolicitudTutoriaCreada`, identificado
como `evt-001`, para la solicitud ficticia número `42`.
Ambos suscriptores recibieron el mismo contenido.

El procedimiento, las versiones utilizadas, las capturas y
el alcance de la comprobación se describen en el
[README de la práctica MQTT](evidencias/carlos-mqtt/evidencia%20ejercicio/README.md).

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

## Material grupal pendiente de integración

- Completar las secciones de JMS y AMQP.
- Integrar los apartados de análisis exigidos por la guía.
- Incorporar el documento independiente de notas, de 2 a
  4 páginas, con su fuente LaTeX y PDF.
- Incluir en las notas la tabla de aportes, la declaración
  de IA y la fotografía del bosquejo inicial.
- Registrar las instrucciones de compilación del documento
  de notas cuando se incorpore.
- Añadir el enlace accesible al video del ensayo grupal.

## Video del ensayo

**Enlace:** pendiente de incorporación.