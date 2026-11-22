# Práctica AMQP con RabbitMQ (John)

Un evento `PrepostulacionAprobada` del SSDC (datos ficticios) se publica en un exchange `topic` y llega a tres colas. No se conecta al SSDC real.

## Requisitos
- Docker Desktop abierto.
- Python 3 y la librería: `pip install pika`

## Pasos (carpeta `john/practica-rabbitmq`)

1. Levantar el broker: `docker compose up -d`
2. Abrir http://localhost:15672 (usuario `demo`, clave `demo-local`). **Captura 1:** pestaña *Overview* con el broker en marcha.
3. Abrir tres terminales y ejecutar, una en cada una:
   - `python consumidor.py q.correo`
   - `python consumidor.py q.notificaciones`
   - `python consumidor.py q.auditoria`
4. En una cuarta terminal publicar tres eventos:
   - `python productor.py` (prepostulacion.aprobada)
   - `python productor.py prepostulacion.rechazada`
   - `python productor.py proceso.cerrado`
5. **Captura 2:** las cuatro terminales juntas. Resultado esperado:
   - aprobada → llega a las tres colas;
   - rechazada → llega a `q.correo` y `q.auditoria`;
   - proceso.cerrado → solo llega a `q.auditoria`.
6. **Captura 3:** en el panel, pestaña *Exchanges* → `ssdc.eventos` → *Bindings*, y pestaña *Queues*.

### Experimento de fallo A: consumidor caído
1. Cierra la terminal de `q.correo` (Ctrl+C) y publica `python productor.py`.
2. En el panel, *Queues*: `q.correo` muestra 1 en *Ready*. **Captura 4.**
3. Vuelve a ejecutar `python consumidor.py q.correo`: recibe el mensaje que se quedó esperando.

### Experimento de fallo B: error de procesamiento
1. Cierra `q.notificaciones` y ejecútalo con `python consumidor.py q.notificaciones --falla`.
2. Publica `python productor.py`. El consumidor rechaza el mensaje y el broker lo manda a `q.fallidos`.
3. En el panel, `q.fallidos` muestra 1 en *Ready*. **Captura 5.** Lo lees con `python consumidor.py q.fallidos`.

### Limpiar
`docker compose down`

## Qué demuestra y qué no
- Demuestra enrutamiento por *binding*, desacople entre productor y consumidores, que la cola retiene mensajes si el consumidor está caído y el uso de una cola de fallidos.
- No demuestra alta disponibilidad del broker (hay un solo nodo), rendimiento ni integración con el SSDC.

## Pruebas realizadas
Ejecutada el 20 de noviembre de 2026 por John Silva en su equipo (Windows, Python 3.13, Docker Desktop, RabbitMQ 3.13.7). Resultados: el evento de aprobación llegó a las tres colas, el de rechazo a `q.correo` y `q.auditoria`, y el de cierre solo a `q.auditoria`; con `q.correo` caído, el mensaje esperó en la cola y se entregó al volver el consumidor. Las capturas están en `../evidencias/`. El experimento de la cola de fallidos (B) está implementado pero todavía no tiene capturas propias.
