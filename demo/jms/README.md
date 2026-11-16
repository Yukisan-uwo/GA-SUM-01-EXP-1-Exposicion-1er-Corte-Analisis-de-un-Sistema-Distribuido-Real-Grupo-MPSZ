# Práctica JMS — Nixon Piguave

Un productor Java publica un evento ficticio y dos consumidores Java lo reciben mediante suscripciones independientes al mismo tópico. No requiere ejecutar SGRA ni instalar Java/Maven en Windows: se compila y ejecuta dentro de Docker.

## Qué utilizamos

- API `jakarta.jms`, proveedor Apache Artemis 2.57.0.
- Java 21 dentro de los contenedores; el proyecto compila con nivel de lenguaje Java 17.
- Tópico `sgra.tutorias.creada`, dos suscripciones **no durables**.
- Mensajes `TextMessage` con datos JSON ficticios.
- El cliente elegido se comunica con Artemis mediante su protocolo Core sobre TCP; JMS es la API, no ese protocolo.
- Las etiquetas notificaciones y auditoría representan funciones: solo se imprime el evento, no se envían correos ni se almacena una auditoría real.

## 1. Preparar (una vez)

Abrir **Docker Desktop** y esperar a que el motor esté funcionando. Abrir PowerShell y situarse en esta carpeta:

```powershell
cd 'C:\Users\user\Desktop\7mo Semestre\Aplicaciones distribuidas\Corte 1\GA-SUM-01 — EXP-1 — Exposición 1er Corte\demo\jms'
docker version
docker compose up -d broker
docker compose logs --tail 30 broker
docker compose build cliente
```

La primera vez se descargan imágenes y dependencias; puede tardar varios minutos. Esperar a que el broker anuncie que está activo antes de ejecutar clientes. Si `docker version` muestra que no puede conectar con el motor, abrir Docker Desktop; no continuar con los demás comandos hasta solucionarlo.

Las credenciales públicas `practica` / `practica-local` son únicamente de esta demostración local. La consola está en http://localhost:8162/console . Los puertos se publican solo en localhost. El puerto 61617 del equipo corresponde al 61616 del contenedor. Los clientes se conectan entre contenedores usando `broker:61616`.

Verificación automática opcional antes de tomar las capturas:

```powershell
docker compose run --rm cliente verificar
```

Comprueba que dos suscripciones reciben el mismo cuerpo y JMSMessageID usando un tópico separado y único para no contaminar las capturas del ejemplo.

## 2. Abrir tres terminales en esta misma carpeta

### Terminal A: notificaciones

```powershell
docker compose run --rm cliente suscribir notificaciones
```

Esperar a ver `Suscripcion independiente NO DURABLE. Esperando eventos...` y tomar la captura 02.

### Terminal B: auditoría

```powershell
docker compose run --rm cliente suscribir auditoria
```

Esperar el mismo aviso y tomar la captura 03. Las dos terminales deben permanecer abiertas.

### Terminal C: productor

```powershell
docker compose run --rm cliente publicar 42 evt-001
```

Tomar captura 04. Revisar las terminales A y B y tomar capturas 05 y 06. Las tres deben mostrar `evt-001`, `SolicitudTutoriaCreada`, `solicitudId:42` (en JSON) y el mismo `JMSMessageID`.

Para una segunda publicación:

```powershell
docker compose run --rm cliente publicar 43 evt-002
```

Usar un `eventoId` distinto por publicación. No atribuir idempotencia al programa: imprime los mensajes y no implementa deduplicación.

## 3. Seis capturas recomendadas

Guardar en `evidencias/nixon-jms/practica/`, desde la raíz del proyecto. No hay una cantidad oficial de capturas de práctica en la guía: seis es nuestra propuesta para documentar los pasos.

| N.º y nombre | Qué debe verse |
|---|---|
| `01-Terminal-C-entorno-broker.png` | `docker compose ps` y `docker compose logs --tail 15 broker`: broker activo, imagen/servicio y puerto. Puede acompañarse de `docker version`. |
| `02-Terminal-A-notificaciones-esperando.png` | Comando, nombre notificaciones, tópico y aviso de espera. |
| `03-Terminal-B-auditoria-esperando.png` | Comando, nombre auditoría, tópico y aviso de espera. |
| `04-Terminal-C-publicacion-evt-001.png` | Comando publicar, evento JSON y JMSMessageID. |
| `05-Terminal-A-notificaciones-recibe-evt-001.png` | `RECIBIDO por notificaciones`, JSON y JMSMessageID. |
| `06-Terminal-B-auditoria-recibe-evt-001.png` | `RECIBIDO por auditoria`, mismo JSON y JMSMessageID. |

Hacer las capturas después de ejecutar realmente los pasos. No reemplazar resultados por texto esperado. Si algo falla, conservar la evidencia y describir el fallo. No se han creado capturas ficticias.

## 4. Prueba opcional de desconexión (tres capturas adicionales)

1. Después de la prueba básica, detener **solo auditoría** con Ctrl+C en B. Notificaciones permanece conectada.
2. En C, publicar `docker compose run --rm cliente publicar 44 evt-003`. Tomar una captura donde se vea publicación y recepción en notificaciones: `07-publicacion-con-auditoria-desconectada.png`.
3. Reiniciar auditoría con su comando y observar que `evt-003` no reaparece en esta suscripción no durable. Tomar `08-auditoria-reconectada.png`. La captura por sí sola no prueba una ausencia indefinida: documentar el orden de los pasos y el tiempo observado.
4. Publicar `docker compose run --rm cliente publicar 45 evt-004`. Ambos deben recibir este nuevo evento. Tomar `09-recepcion-despues-de-reconexion.png`, mostrando ambos terminales.

Esto ilustra una limitación de la **suscripción no durable**. Marcar el mensaje como persistente no conserva por sí solo una suscripción que ya no existe. No es una prueba de recuperación del broker ni un incidente histórico real. El volumen conserva datos/configuración del broker entre reinicios, pero no convierte a estos consumidores en durables.

## 5. Terminar

En A y B usar Ctrl+C. En C:

```powershell
docker compose down
```

No se elimina el volumen de datos. No ejecutar `down -v` para una pausa normal. Evitar dos clientes de notificaciones o dos de auditoría simultáneamente: reutilizarían el mismo clientID.

## Explicación

1. La API JMS se usa en Java; Artemis implementa el proveedor.
2. El tópico permite que dos suscripciones independientes reciban el mismo evento.
3. Ver el mismo JMSMessageID permite relacionar publicación y recepciones en esta prueba.
4. En esta práctica se usa `receive()`, una recepción **síncrona y bloqueante** en cada consumidor; el productor no llama directamente a sus funciones. No confundir esto con un listener asíncrono de Spring.
5. La publicación confirmada no prueba que todas las operaciones de negocio finalizaron.
6. La desconexión requiere decidir durabilidad, reintentos y deduplicación; no los asumimos implementados.

## Fuentes técnicas

- https://artemis.apache.org/components/artemis/documentation/latest/using-jms.html
- https://artemis.apache.org/components/artemis/documentation/latest/client-classpath
- https://artemis.apache.org/components/artemis/documentation/latest/docker.html
- https://jakarta.ee/specifications/messaging/3.1/jakarta-messaging-spec-3.1.html


