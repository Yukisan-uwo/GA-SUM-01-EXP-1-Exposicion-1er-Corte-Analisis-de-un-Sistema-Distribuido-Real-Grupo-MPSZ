# Nixon Piguave — JMS aplicado al RTV

Este aporte estudia Jakarta Messaging como alternativa para distribuir eventos del Sistema de Revisión Técnica Vehicular del Municipio de Valencia. El análisis define el evento `InspeccionIniciada` y su relación con el prototipo REST existente.

## Archivos de este avance

- `analisis-rtv-jms.md`: flujo observado, evento propuesto y decisiones de entrega y consistencia.
- `fuentes-nixon.bib`: fuentes utilizadas en este aporte. Las entradas compartidas no deben duplicarse.
- `practica-jms-rtv/`: productor Java, dos suscriptores y entorno Docker reproducible.
- `evidencias/practica/`: seis capturas de la ejecución realizada por mi y registros de la comprobación previa.
- `../secciones/nixon.tex`: las tres diapositivas integradas a la presentación grupal, con diagramas TikZ.

El caso RTV es una propuesta de aplicación de JMS. El prototipo consultado autoriza el inicio por HTTP/JSON y persiste la inspección en PostgreSQL. No se implementó un broker en ese prototipo para este avance.

## Estado de la práctica

La práctica anterior, en `../demo/jms/`, y las seis capturas de `../evidencias/nixon-jms/practica/` usan un evento de tutoría. Se conservan sin cambios. No acreditan la ejecución del nuevo ejemplo RTV.

El 21/11/2026 se ejecutó la nueva práctica independiente con Apache Artemis: auditoría e indicadores recibieron el mismo cuerpo y JMSMessageID para `evt-rtv-001`. Los registros están en `evidencias/practica/`. Se reprodujo el flujo y aportó las seis capturas de pantalla, guardadas en esa misma carpeta. En las capturas 4, 5 y 6 coinciden el cuerpo y el JMSMessageID `ID:698a6d8e-e575-11f1-b4d4-e626e217634f`. Los archivos .log corresponden a una ejecución previa separada. La prueba no conecta los servicios reales del RTV.

## Integración al repositorio grupal

Subir esta carpeta y el archivo actualizado `secciones/nixon.tex`, conservando sus rutas desde la raíz. La sección del equipo ya se carga desde el archivo principal. No subir una segunda copia de las diapositivas dentro de esta carpeta.

La bibliografía compartida necesita las claves `jakarta2022`, `springjms` y `maharjan2023`. La entrada bibliográfica del repositorio RTV fue retirada; conservar las referencias de los compañeros.

La compilación final del equipo sigue siendo `pdflatex main.tex`, `biber main`, `pdflatex main.tex`, `pdflatex main.tex`. Este avance no sustituye el `main.tex` del equipo.

## Avances

1. Análisis del RTV y adaptación conceptual de la sección JMS.
2. Práctica JMS del RTV ejecutada y diapositiva del ejemplo actualizada con el resultado observado. Seis capturas de ejecución incorporadas.

Quedan la evidencia de lectura, el guion para las notas y el ensayo. Las capturas anteriores de tutorías se conservan con su significado original.



