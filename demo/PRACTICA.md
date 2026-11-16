# Práctica PUB/SUB basada en un evento ficticio de SGRA

**Alcance:** se simula `SolicitudTutoriaCreada`; no se conecta con el SGRA verdadero.
La configuración habilita acceso anónimo por simplicidad, **solo para localhost**.
Nunca exponer este broker a Internet ni usar datos personales reales.

## Requisito

Docker Desktop con Docker Compose; alternativamente, instalar Mosquitto broker y
sus clientes `mosquitto_pub` y `mosquitto_sub` localmente.

## Iniciar broker

```sh
cd demo
docker compose up -d
```

## Tres terminales

**Terminal A / notificaciones:**

```sh
docker exec -it sgra-mqtt-broker mosquitto_sub -t sgra/tutorias/creada -v
```

**Terminal B / auditoría:**

```sh
docker exec -it sgra-mqtt-broker mosquitto_sub -t sgra/tutorias/creada -v
```

**Terminal C / publicador (ejecutar cuando A y B ya estén suscritos):**

```sh
docker exec sgra-mqtt-broker mosquitto_pub -t sgra/tutorias/creada -m 'Solicitud 42 creada'
```

**Observación esperada:** A y B muestran el mismo tópico y mensaje. Registra
el resultado real de tu ejecución; **no lo declares como verificado antes de probarlo**.

## Prueba opcional de fallo

1. Con A y B en ejecución, detener el broker: `docker compose stop broker`.
2. Intentar publicar y observar el error de conexión.
3. Reiniciar: `docker compose start broker`.
4. Volver a suscribir los clientes y publicar.
5. No afirmar recuperación automática de mensajes: la configuración de esta demo no los persiste.

## Cerrar

```sh
docker compose down
```

Fuentes: manuales oficiales de Eclipse Mosquitto (en `referencias.bib`).
