# Nixon Piguave — JMS aplicado al RTV

Este aporte estudia Jakarta Messaging como alternativa para distribuir eventos del Sistema de Revisión Técnica Vehicular del Municipio de Valencia. El primer avance define el evento `InspeccionIniciada` y su relación con el prototipo REST existente.

## Archivos de este avance

- `analisis-rtv-jms.md`: flujo observado, evento propuesto y decisiones de entrega y consistencia.
- `fuentes-nixon.bib`: fuentes utilizadas en este aporte. Las entradas compartidas no deben duplicarse.
- `../secciones/nixon.tex`: las tres diapositivas integradas a la presentación grupal, con diagramas TikZ.

El caso RTV es una propuesta de aplicación de JMS. El prototipo consultado autoriza el inicio por HTTP/JSON y persiste la inspección en PostgreSQL. No se implementó un broker en ese prototipo para este avance.

## Estado de la práctica

La práctica anterior, en `../demo/jms/`, y las seis capturas de `../evidencias/nixon-jms/practica/` usan un evento de tutoría. Se conservan sin cambios. No acreditan la ejecución del nuevo ejemplo RTV.

El siguiente avance preparará un productor y dos consumidores con datos ficticios de inspección, y documentará su ejecución con capturas nuevas. La prueba seguirá siendo independiente del sistema RTV real.

## Integración al repositorio grupal

Subir esta carpeta y el archivo actualizado `secciones/nixon.tex`, conservando sus rutas desde la raíz. La sección del equipo ya se carga desde el archivo principal. No subir una segunda copia de las diapositivas dentro de esta carpeta.

La bibliografía compartida necesita las claves `jakarta2022`, `springjms`, `maharjan2023` y `rtv_prototipo_nixon`. Las primeras tres ya estaban en la copia revisada. La última corresponde al código del RTV consultado y se incorpora como entrada nueva; conservar las referencias de los compañeros.

La compilación final del equipo sigue siendo `pdflatex main.tex`, `biber main`, `pdflatex main.tex`, `pdflatex main.tex`. Este avance no sustituye el `main.tex` del equipo.

## Alcance del primer commit

Análisis del RTV y adaptación conceptual de la sección JMS. La nueva práctica, su evidencia de lectura, el guion para las notas y el ensayo se completarán en avances posteriores. El código Java y las capturas anteriores no se renombran para aparentar una ejecución nueva.
