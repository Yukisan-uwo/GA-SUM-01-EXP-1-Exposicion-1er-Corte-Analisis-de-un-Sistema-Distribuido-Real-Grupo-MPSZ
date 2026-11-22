# Mensajería Publish/Subscribe: MQTT, JMS y AMQP

**Universidad Técnica Estatal de Quevedo (UTEQ)**  
**Asignatura:** Aplicaciones Distribuidas (ISR-701)  
**Actividad:** GA-SUM-02 / EXP-1 — Exposición del primer corte  
**Tema:** Publish/Subscribe y tecnologías de mensajería aplicadas a proyectos académicos  
**Periodo:** Noviembre de 2026

## Integrantes y distribución de la exposición

| Integrante | Tema | Proyecto de aplicación | Archivo de diapositivas |
|---|---|---|---|
| **Zambrano Parraga Luis Mario** | Fundamentos de Publish/Subscribe (PUB/SUB) | SGRA — Sistema de Gestión de Refuerzo Académico | [`secciones/mario.tex`](secciones/mario.tex) |
| **Manotoa Alava Carlos Alexander** | MQTT | CLAB — Gestión de laboratorios informáticos | [`secciones/carlos.tex`](secciones/carlos.tex) |
| **Piguave Montaño Nixon Antonio** | JMS / Jakarta Messaging | RTV — Revisión técnica vehicular | [`secciones/nixon.tex`](secciones/nixon.tex) |
| **Silva Triviño John Jairo** | AMQP y RabbitMQ; comparación e incidente de sistemas distribuidos | SSDC — flujo de prepostulaciones | [`secciones/john.tex`](secciones/john.tex) |

Cada integrante presenta los fundamentos de su tema, una práctica breve de mensajería y su aplicación o propuesta de integración al proyecto correspondiente. La exposición grupal se organiza para un máximo de **12 minutos** (aproximadamente tres por integrante), seguidos de preguntas.

## Material de la exposición

- **Diapositivas compiladas:** [`main.pdf`](main.pdf).
- **Fuente principal de las diapositivas:** [`main.tex`](main.tex).
- **Notas grupales compiladas:** [`notas.pdf`](notas.pdf).
- **Fuente de las notas:** [`notas.tex`](notas.tex), que incorpora los cuatro apartados de [`notas/`](notas/).
- **Bibliografía compartida:** [`referencias.bib`](referencias.bib), en estilo IEEE con `biblatex` y Biber.
- **Registro de trabajo:** [`BITACORA.md`](BITACORA.md) y las bitácoras individuales incluidas en las carpetas de los integrantes.
- **Bosquejo inicial de arquitectura:** [`evidencias/Boceto_inicial_a_papel.jpg`](evidencias/Boceto_inicial_a_papel.jpg), incorporado a las notas grupales.

Los esquemas de las diapositivas se elaboraron en LaTeX/TikZ, de modo que pueden editarse desde los archivos fuente.

## Prácticas y evidencias

### 1. Mario — PUB/SUB aplicado al SGRA

La práctica pequeña utiliza **Eclipse Mosquitto**: una terminal publica un evento ficticio `SolicitudTutoriaCreada` en `sgra/tutorias/creada` y otras dos terminales, que representan notificaciones y auditoría, reciben el mensaje.

- Instrucciones de la práctica pequeña: [`demo/PRACTICA.md`](demo/PRACTICA.md).
- Capturas de ejecución y lectura académica: [`evidencias/Mario Practica/`](evidencias/Mario%20Practica/).
- Práctica experimental adicional Java–MQTT: [`demo/sgra-mqtt/README.md`](demo/sgra-mqtt/README.md).
- Instrucciones de integración: [`demo/sgra-mqtt/INTEGRACION.md`](demo/sgra-mqtt/INTEGRACION.md).
- Resultados y limitaciones: [`evidencias/Mario Practica/practica-grande-SGRA/`](evidencias/Mario%20Practica/practica-grande-SGRA/).

La integración experimental Java–MQTT incluyó pruebas de publicación, caída y reinicio del broker, duplicados y pruebas automatizadas. **No equivale a un despliegue productivo:** la persistencia del servicio se simuló en las pruebas y el código compartido requiere incorporarse al backend SGRA original para reproducir esa integración.

### 2. Carlos — MQTT aplicado a CLAB

La demostración usa **Mosquitto y MQTTX** para publicar `ReporteFallaResuelto` en el tópico `clab/fallas/resuelta` y mostrar la recepción en dos suscriptores con QoS 1.

- Procedimiento: [`demo/PRACTICA-CLAB.md`](demo/PRACTICA-CLAB.md).
- Capturas de práctica y lecturas anotadas: [`evidencias/carlos-mqtt/`](evidencias/carlos-mqtt/).

Los clientes representan componentes de CLAB; la comunicación MQTT se presenta como **propuesta de integración**, no como funcionalidad ya desplegada en ese sistema.

### 3. Nixon — JMS aplicado al RTV

La práctica utiliza **Jakarta Messaging con Apache ActiveMQ Artemis** para publicar `InspeccionIniciada` en `rtv.inspecciones.iniciada` y distribuirlo a suscriptores independientes de auditoría e indicadores.

- Proyecto Java, configuración y pasos de ejecución: [`Nixon Piguave/practica-jms-rtv/`](Nixon%20Piguave/practica-jms-rtv/).
- Evidencias de la ejecución y lectura: [`Nixon Piguave/evidencias/`](Nixon%20Piguave/evidencias/).
- Análisis de aplicación al RTV: [`Nixon Piguave/analisis-rtv-jms.md`](Nixon%20Piguave/analisis-rtv-jms.md).

El evento usa datos ficticios. La práctica es independiente del RTV y **no demuestra** que el sistema real almacene una inspección o publique eventos JMS actualmente.

### 4. John — AMQP/RabbitMQ aplicado al SSDC

La práctica utiliza **RabbitMQ, Docker y Python** para enviar eventos de prepostulación a varias colas mediante un exchange `topic` y reglas de enrutamiento (*bindings*). También se documenta el comportamiento con un consumidor temporalmente desconectado.

- Archivos ejecutables e instrucciones: [`John Jairo Silva Triviño/practica-rabbitmq/`](John%20Jairo%20Silva%20Trivi%C3%B1o/practica-rabbitmq/).
- Capturas: [`John Jairo Silva Triviño/evidencias/`](John%20Jairo%20Silva%20Trivi%C3%B1o/evidencias/).
- Análisis del caso SSDC: [`John Jairo Silva Triviño/analisis-ssdc-eventos.md`](John%20Jairo%20Silva%20Trivi%C3%B1o/analisis-ssdc-eventos.md).

El SSDC actual **no utiliza un broker RabbitMQ**: se propone como evolución de su flujo de notificaciones. La exposición incluye además una comparación de tecnologías y un incidente documentado de Google Cloud como referencia para analizar fallos distribuidos.

## Estructura principal del repositorio

```text
.
├── main.tex                    # Presentación principal Beamer
├── main.pdf                    # Diapositivas compiladas
├── notas.tex                   # Documento de notas del equipo
├── notas.pdf                   # Notas compiladas
├── referencias.bib             # Bibliografía IEEE
├── BITACORA.md                # Registro grupal de actividades
├── README.md
├── secciones/
│   ├── mario.tex
│   ├── carlos.tex
│   ├── nixon.tex
│   └── john.tex
├── notas/
│   ├── Mario.tex
│   ├── carlos.tex
│   ├── nixon.tex
│   └── john.tex
├── demo/                       # Prácticas PUB/SUB, MQTT y archivos de apoyo
├── evidencias/
│   ├── Boceto_inicial_a_papel.jpg
│   ├── Mario Practica/
│   ├── carlos-mqtt/
│   └── nixon-jms/
├── Nixon Piguave/              # Desarrollo y evidencias individuales JMS–RTV
└── John Jairo Silva Triviño/   # Desarrollo y evidencias individuales AMQP–SSDC
```

## Recompilación de los documentos LaTeX

Requiere una distribución de LaTeX con **pdfLaTeX**, **Biber** y los paquetes `beamer`, `tikz/pgf`, `babel` (español), `lmodern`, `biblatex`, `biblatex-ieee`, `csquotes`, `booktabs`, `tabularx`, `geometry`, `graphicx`, `hyperref` y `xurl`.

Ejecutar desde la raíz del repositorio.

### Diapositivas

```bash
pdflatex -interaction=nonstopmode -halt-on-error main.tex
biber main
pdflatex -interaction=nonstopmode -halt-on-error main.tex
pdflatex -interaction=nonstopmode -halt-on-error main.tex
```

Archivo generado: `main.pdf`.

### Notas grupales

```bash
pdflatex -interaction=nonstopmode -halt-on-error notas.tex
biber notas
pdflatex -interaction=nonstopmode -halt-on-error notas.tex
pdflatex -interaction=nonstopmode -halt-on-error notas.tex
```

Archivo generado: `notas.pdf`. El documento incluye los guiones, la tabla de aportes, las declaraciones de uso de IA, las referencias y el bosquejo.

En Overleaf, seleccionar `main.tex` o `notas.tex` como documento principal, usar pdfLaTeX y procesar la bibliografía con Biber. Conservar exactamente los nombres y las mayúsculas/minúsculas de las rutas.

## Alcance, fuentes y uso de IA

Las demostraciones utilizan datos de ejemplo y entornos locales. Debe distinguirse entre las funciones documentadas de cada proyecto, la **simulación de mensajería**, la **integración experimental realizada en el SGRA** y las **propuestas de evolución** para los distintos sistemas. Las pruebas no equivalen a métricas de producción ni a garantías generales de disponibilidad o consistencia.

Las fuentes académicas y técnicas utilizadas se consignan en [`referencias.bib`](referencias.bib). Las capturas de lectura y las evidencias de práctica se encuentran en las carpetas respectivas. Las declaraciones de uso de herramientas de inteligencia artificial de los cuatro integrantes están en [`notas.tex`](notas.tex), complementadas por la bitácora y la documentación individual.

## Video del ensayo grupal

**Enlace:** https://youtu.be/N8fyBjX9-Ms

Enlace proporcionado por el equipo para documentar su ensayo de exposición. Verificar antes de la entrega que pueda abrirse sin restricciones de acceso.
