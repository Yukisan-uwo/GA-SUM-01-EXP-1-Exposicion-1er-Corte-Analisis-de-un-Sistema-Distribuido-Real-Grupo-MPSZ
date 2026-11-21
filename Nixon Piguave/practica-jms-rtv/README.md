# Práctica JMS del RTV

Productor y dos suscriptores Java con Jakarta Messaging y Apache Artemis 2.57.0. Se publica un evento ficticio `InspeccionIniciada` al tópico `rtv.inspecciones.iniciada`.

La demostración se ejecuta separada del PFC. Auditoría e indicadores imprimen el evento recibido; no guardan auditorías ni actualizan una base de indicadores. Las suscripciones son independientes y no durables: iniciar ambas antes de publicar. Marcar el mensaje como persistente no conserva una suscripción no durable desconectada.

## Preparar el entorno

Requiere Docker Desktop iniciado y Docker Compose. Abrir tres terminales PowerShell en esta carpeta. Java y Maven se utilizan dentro de Docker.

Terminal C:

```powershell
docker compose up -d broker
docker compose build cliente
docker compose ps
docker compose logs --tail 25 broker
```

Esperar a que el registro confirme que el servidor Artemis inició. Los puertos de esta práctica son `127.0.0.1:61618` (mensajería) y `127.0.0.1:8163` (consola), diferentes de la práctica anterior. La cuenta local de demostración es `practica` / `practica-local`.

## Suscribir y publicar

Terminal A — auditoría:

```powershell
docker compose run --rm cliente suscribir auditoria
```

Terminal B — indicadores:

```powershell
docker compose run --rm cliente suscribir indicadores
```

Esperar a que ambas muestren `Esperando eventos`. Terminal C — productor:

```powershell
docker compose run --rm cliente publicar 42 evt-rtv-001
```

Comparar `eventoId`, cuerpo y `JMSMessageID` en las tres terminales. Los UUID y las fechas se generan en cada envío, por lo que una nueva ejecución tendrá otros valores. El envío al broker y la recepción no demuestran una autorización REST ni una inspección guardada en el PFC.

## Seis capturas

Guardarlas en `../evidencias/practica/`. Mostrar el comando, el rol y su salida completos. Maximizar la ventana y reducir el tamaño de letra si el JSON no cabe.

| Momento | Nombre |
|---|---|
| C: broker iniciado y `docker compose ps` | `01-Terminal-C-entorno-broker-rtv.png` |
| A: auditoría esperando, antes del envío | `02-Terminal-A-auditoria-esperando.png` |
| B: indicadores esperando, antes del envío | `03-Terminal-B-indicadores-esperando.png` |
| C: publicación de `evt-rtv-001` | `04-Terminal-C-publicacion-evt-rtv-001.png` |
| A: recepción del mismo evento | `05-Terminal-A-auditoria-recibe-evt-rtv-001.png` |
| B: recepción del mismo evento | `06-Terminal-B-indicadores-recibe-evt-rtv-001.png` |

Las capturas de lectura de artículos son otra evidencia y no se sustituyen con estas imágenes.

## Comprobación automática adicional

```powershell
docker compose run --rm cliente verificar
```

Crea un tópico temporal de nombre único y dos suscripciones. Termina con código 0 si ambas reciben el mismo cuerpo y JMSMessageID, o con código 1 si la comprobación falla. Se usa para verificar el entorno; las capturas del flujo anterior corresponden a procesos separados.

## Detener

En A y B usar Ctrl+C. Después, en C:

```powershell
docker compose down
```

Este comando conserva el volumen del broker.

## Fuentes técnicas

- [Jakarta Messaging 3.1](https://jakarta.ee/specifications/messaging/3.1/).
- [Artemis: cliente Java](https://artemis.apache.org/components/artemis/documentation/latest/client-classpath).
- [Artemis: Docker](https://artemis.apache.org/components/artemis/documentation/latest/docker.html).
