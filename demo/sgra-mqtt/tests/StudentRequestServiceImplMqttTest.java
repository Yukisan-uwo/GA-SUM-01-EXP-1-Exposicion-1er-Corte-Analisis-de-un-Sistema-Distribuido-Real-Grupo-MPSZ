
package com.CLMTZ.Backend.service.reinforcement.student.impl;

import com.CLMTZ.Backend.dto.reinforcement.student.*;
import com.CLMTZ.Backend.repository.reinforcement.student.StudentRequestRepository;
import com.CLMTZ.Backend.service.external.IEmailService;
import com.CLMTZ.Backend.service.external.IStorageService;
import com.CLMTZ.Backend.service.reinforcement.student.StudentCatalogService;
import com.CLMTZ.Backend.service.reinforcement.student.SgraMqttPublisher;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentRequestServiceImplMqttTest {

    @Mock
    private StudentRequestRepository repository;

    @Mock
    private StudentCatalogService catalogo;

    @Mock
    private IStorageService almacenamiento;

    @Mock
    private IEmailService correos;

    @Mock
    private SgraMqttPublisher publicador;

    private StudentRequestServiceImpl servicio;

    @BeforeEach
    void configurar() {
        servicio = new StudentRequestServiceImpl(
                repository,
                catalogo,
                almacenamiento,
                correos,
                publicador
        );
    }

    private StudentRequestCreateRequestDTO solicitud() {
        StudentRequestCreateRequestDTO req =
                new StudentRequestCreateRequestDTO();

        req.setSubjectId(8);
        req.setSessionTypeId(1);
        req.setReason("Necesito refuerzo en programación");

        return req;
    }

    private void prepararDatos() {
        when(catalogo.getTeacherForSubject(8))
                .thenReturn(new StudentSubjectTeacherDTO(
                        18, "Docente de prueba", "docente@example.test"
                ));

        when(catalogo.getActivePeriod())
                .thenReturn(new ActivePeriodDTO(12, "2026"));
    }

    private void simularRegistroExitoso() {
        prepararDatos();

        when(repository.createRequest(
                5, 8, 18, 1,
                "Necesito refuerzo en programación", 12
        )).thenReturn(42);
    }

    @Test
    void publicaEventoCuandoSolicitudSeRegistra()
            throws Exception {

        simularRegistroExitoso();

        StudentRequestCreateResponseDTO resultado =
                servicio.create(solicitud(), 5, null);

        assertEquals(42, resultado.getRequestId());

        verify(publicador, times(1))
                .publicarSolicitudCreada(42);

        verifyNoInteractions(correos, almacenamiento);
    }

    @Test
    void noPublicaEventoSiFallaRegistro() throws Exception {

        prepararDatos();

        when(repository.createRequest(
                5, 8, 18, 1,
                "Necesito refuerzo en programación", 12
        )).thenThrow(
                new IllegalStateException("Fallo simulado de registro")
        );

        assertThrows(
                IllegalStateException.class,
                () -> servicio.create(solicitud(), 5, null)
        );

        verifyNoInteractions(publicador);
    }

    @Test
    void solicitudRetornaAunqueFallePublicacionMqtt()
            throws Exception {

        simularRegistroExitoso();

        doThrow(new MqttException(32103))
                .when(publicador)
                .publicarSolicitudCreada(42);

        StudentRequestCreateResponseDTO resultado =
                servicio.create(solicitud(), 5, null);

        assertEquals(42, resultado.getRequestId());

        verify(publicador, times(1))
                .publicarSolicitudCreada(42);
    }
}
