
package com.CLMTZ.Backend.demo;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

public class MqttPruebaSgra {

    public static void main(String[] args) throws Exception {

        String broker = "tcp://127.0.0.1:1883";
        String topico = "sgra/tutorias/creada";

        // Datos ficticios de esta prueba.
        int solicitudId = 42;
        String eventoId = UUID.randomUUID().toString();
        //P05 — eventos duplicados
        //String eventoId = "evt-sgra-duplicado-001";

        String evento = """
            {
              "eventoId": "%s",
              "tipo": "SolicitudTutoriaCreada",
              "solicitudId": %d,
              "fecha": "%s"
            }
            """.formatted(
                eventoId,
                solicitudId,
                Instant.now().toString()
        );

        String clienteId = "sgra-java-" +
                UUID.randomUUID().toString().substring(0, 8);

        MqttClient cliente = new MqttClient(
                broker,
                clienteId,
                new MemoryPersistence()
        );

        try {
            MqttConnectOptions opciones =
                    new MqttConnectOptions();
            opciones.setCleanSession(true);

            cliente.connect(opciones);

            MqttMessage mensaje = new MqttMessage(
                    evento.getBytes(StandardCharsets.UTF_8)
            );

            mensaje.setQos(1);
            mensaje.setRetained(false);

            cliente.publish(topico, mensaje);

            System.out.println("PUBLICACION MQTT COMPLETADA");
            System.out.println("Topico: " + topico);
            System.out.println("Mensaje:");
            System.out.println(evento);

        } finally {
            if (cliente.isConnected()) {
                cliente.disconnect();
            }
            cliente.close();
        }
    }
}
