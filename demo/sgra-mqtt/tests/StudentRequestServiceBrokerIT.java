
package com.CLMTZ.Backend.service.reinforcement.student.impl;

import com.CLMTZ.Backend.dto.reinforcement.student.*;
import com.CLMTZ.Backend.repository.reinforcement.student.StudentRequestRepository;
import com.CLMTZ.Backend.service.external.IEmailService;
import com.CLMTZ.Backend.service.external.IStorageService;
import com.CLMTZ.Backend.service.reinforcement.student.StudentCatalogService;
import com.CLMTZ.Backend.service.reinforcement.student.SgraMqttPublisher;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentRequestServiceBrokerIT {

    private static final String BROKER = "tcp://127.0.0.1:1883";
    private static final String TOPICO = "sgra/tutorias/creada";

    @Mock
    StudentRequestRepository repository;

    @Mock
    StudentCatalogService catalogo;

    @Mock
    IStorageService almacenamiento;

    @Mock
    IEmailService correos;

    @Test
    void servicioPublicaYDosSuscriptoresReciben() throws Exception {

        // Simular los datos necesarios del SGRA.
        when(catalogo.getTeacherForSubject(8))
                .thenReturn(new StudentSubjectTeacherDTO(
                        18, "Docente de prueba", "docente@example.test"
                ));

        when(catalogo.getActivePeriod())
                .thenReturn(new ActivePeriodDTO(12, "2026"));

        when(repository.createRequest(
                5, 8, 18, 1,
                "Necesito refuerzo en programación", 12
        )).thenReturn(42);

        // Preparar dos consumidores MQTT reales.
        CountDownLatch recepciones = new CountDownLatch(2);

        AtomicReference<String> eventoA = new AtomicReference<>();
        AtomicReference<String> eventoB = new AtomicReference<>();

        MqttClient notificaciones = new MqttClient(
                BROKER,
                "test-noti-" + UUID.randomUUID(),
                new MemoryPersistence()
        );

        MqttClient auditoria = new MqttClient(
                BROKER,
                "test-audit-" + UUID.randomUUID(),
                new MemoryPersistence()
        );

        try {
            notificaciones.connect();
            auditoria.connect();

            notificaciones.subscribe(TOPICO, 1, (topic, mensaje) -> {
                eventoA.set(new String(
                        mensaje.getPayload(), StandardCharsets.UTF_8
                ));
                recepciones.countDown();
            });

            auditoria.subscribe(TOPICO, 1, (topic, mensaje) -> {
                eventoB.set(new String(
                        mensaje.getPayload(), StandardCharsets.UTF_8
                ));
                recepciones.countDown();
            });

            // Publicador MQTT real, habilitado solo para esta prueba.
            SgraMqttPublisher publicadorReal =
                    new SgraMqttPublisher(true, BROKER);

            StudentRequestServiceImpl servicio =
                    new StudentRequestServiceImpl(
                            repository,
                            catalogo,
                            almacenamiento,
                            correos,
                            publicadorReal
                    );

            StudentRequestCreateRequestDTO solicitud =
                    new StudentRequestCreateRequestDTO();

            solicitud.setSubjectId(8);
            solicitud.setSessionTypeId(1);
            solicitud.setReason("Necesito refuerzo en programación");

            // Ejecutar el flujo de negocio del SGRA.
            StudentRequestCreateResponseDTO resultado =
                    servicio.create(solicitud, 5, null);

            assertEquals(42, resultado.getRequestId());

            // Verificar la recepción por ambos consumidores.
            assertTrue(
                    recepciones.await(10, TimeUnit.SECONDS),
                    "No llegaron los mensajes a ambos suscriptores"
            );

            assertNotNull(eventoA.get());
            assertEquals(eventoA.get(), eventoB.get());

            assertTrue(eventoA.get().contains("SolicitudTutoriaCreada"));
            assertTrue(eventoA.get().contains("\"solicitudId\": 42"));

            verify(repository).createRequest(
                    5, 8, 18, 1,
                    "Necesito refuerzo en programación", 12
            );

            System.out.println("P06-C SUPERADA");
            System.out.println("Servicio SGRA -> MQTT -> 2 suscriptores");
            System.out.println("Evento recibido:");
            System.out.println(eventoA.get());

        } finally {
            if (notificaciones.isConnected()) {
                notificaciones.disconnect();
            }
            notificaciones.close();

            if (auditoria.isConnected()) {
                auditoria.disconnect();
            }
            auditoria.close();
        }
    }
}
