
package com.CLMTZ.Backend.service.reinforcement.student;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

@Component
public class SgraMqttPublisher {

    private final boolean enabled;
    private final String brokerUrl;

    public SgraMqttPublisher(
            @Value("${sgra.mqtt.enabled:false}") boolean enabled,
            @Value("${sgra.mqtt.broker:tcp://127.0.0.1:1883}")
            String brokerUrl) {
        this.enabled = enabled;
        this.brokerUrl = brokerUrl;
    }

    public void publicarSolicitudCreada(Integer solicitudId)
            throws MqttException {

        if (!enabled) {
            return;
        }

        String eventoId = UUID.randomUUID().toString();

        String payload = """
                {
                  "eventoId": "%s",
                  "tipo": "SolicitudTutoriaCreada",
                  "solicitudId": %d,
                  "fecha": "%s"
                }
                """.formatted(
                eventoId,
                solicitudId,
                Instant.now()
        );

        MqttClient cliente = new MqttClient(
                brokerUrl,
                "sgra-backend-" + UUID.randomUUID()
        );

        try {
            MqttConnectOptions opciones = new MqttConnectOptions();
            opciones.setConnectionTimeout(3);
            cliente.connect(opciones);

            MqttMessage mensaje = new MqttMessage(
                    payload.getBytes(StandardCharsets.UTF_8)
            );
            mensaje.setQos(1);
            mensaje.setRetained(false);

            cliente.publish("sgra/tutorias/creada", mensaje);

            System.out.println("SGRA publicó evento: " + eventoId);
            System.out.println(payload);

        } finally {
            if (cliente.isConnected()) {
                cliente.disconnect();
            }
            cliente.close();
        }
    }
}
