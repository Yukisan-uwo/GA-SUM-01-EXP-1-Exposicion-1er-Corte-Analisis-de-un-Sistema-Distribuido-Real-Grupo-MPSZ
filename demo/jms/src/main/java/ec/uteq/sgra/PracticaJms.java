package ec.uteq.sgra;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import java.time.Instant;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;
import javax.naming.Context;
import javax.naming.InitialContext;

public final class PracticaJms {
    private static final String TOPICO = "sgra.tutorias.creada";

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException("Uso: suscribir notificaciones|auditoria | publicar 42 evt-001 | verificar");
            }
            ConnectionFactory factory = factory();
            switch (args[0]) {
                case "suscribir" -> {
                    if (args.length != 2 || !Set.of("notificaciones", "auditoria").contains(args[1])) {
                        throw new IllegalArgumentException("Usar: suscribir notificaciones|auditoria");
                    }
                    suscribir(factory, args[1]);
                }
                case "publicar" -> {
                    if (args.length != 3 || !args[2].matches("[A-Za-z0-9_-]{1,80}")) {
                        throw new IllegalArgumentException("Usar: publicar 42 evt-001 (identificador simple)");
                    }
                    publicar(factory, Long.parseLong(args[1]), args[2]);
                }
                case "verificar" -> verificar(factory);
                default -> throw new IllegalArgumentException("Modo desconocido: " + args[0]);
            }
        } catch (Exception error) {
            System.err.println("ERROR: " + error.getClass().getSimpleName() + ": " + error.getMessage());
            System.exit(1);
        }
    }

    private static ConnectionFactory factory() throws Exception {
        Hashtable<String, String> config = new Hashtable<>();
        config.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
        config.put("connectionFactory.ConnectionFactory", env("JMS_URL", "tcp://localhost:61617"));
        InitialContext naming = new InitialContext(config);
        try {
            return (ConnectionFactory) naming.lookup("ConnectionFactory");
        } finally {
            naming.close();
        }
    }

    private static JMSContext conectar(ConnectionFactory factory) {
        return factory.createContext(env("JMS_USER", "practica"), env("JMS_PASSWORD", "practica-local"), JMSContext.AUTO_ACKNOWLEDGE);
    }

    private static void suscribir(ConnectionFactory factory, String nombre) throws Exception {
        try (JMSContext context = conectar(factory)) {
            context.setClientID("nixon-" + nombre);
            Topic topic = context.createTopic(TOPICO);
            try (JMSConsumer consumer = context.createConsumer(topic)) {
                imprimir("SUSCRIPTOR", nombre);
                System.out.println("Suscripcion independiente NO DURABLE. Esperando eventos... Ctrl+C para salir.");
                while (true) {
                    TextMessage message = (TextMessage) consumer.receive();
                    System.out.println("\nRECIBIDO por " + nombre + " | " + Instant.now());
                    System.out.println("JMSMessageID: " + message.getJMSMessageID());
                    System.out.println("JMSRedelivered: " + message.getJMSRedelivered());
                    System.out.println("Mensaje: " + message.getText());
                }
            }
        }
    }

    private static void publicar(ConnectionFactory factory, long solicitud, String evento) throws Exception {
        try (JMSContext context = conectar(factory)) {
            Topic topic = context.createTopic(TOPICO);
            TextMessage message = context.createTextMessage(json(solicitud, evento));
            message.setStringProperty("eventoId", evento);
            context.createProducer().setDeliveryMode(DeliveryMode.PERSISTENT).send(topic, message);
            imprimir("PRODUCTOR", "solicitud " + solicitud);
            System.out.println("PUBLICADO | " + Instant.now());
            System.out.println("JMSMessageID: " + message.getJMSMessageID());
            System.out.println("Mensaje: " + message.getText());
            System.out.println("El envio al proveedor no demuestra que los consumidores completaron sus tareas.");
        }
    }

    private static void verificar(ConnectionFactory factory) throws Exception {
        String unique = UUID.randomUUID().toString();
        String cuerpo = json(999, "verificacion-" + unique);
        try (JMSContext a = conectar(factory); JMSContext b = conectar(factory); JMSContext p = conectar(factory)) {
            Topic topic = p.createTopic("sgra.verificacion." + unique);
            try (JMSConsumer ca = a.createConsumer(topic); JMSConsumer cb = b.createConsumer(topic)) {
                TextMessage enviado = p.createTextMessage(cuerpo);
                p.createProducer().send(topic, enviado);
                TextMessage recibidoA = (TextMessage) ca.receive(10000);
                TextMessage recibidoB = (TextMessage) cb.receive(10000);
                if (recibidoA == null || recibidoB == null
                    || !cuerpo.equals(recibidoA.getText()) || !cuerpo.equals(recibidoB.getText())
                    || !enviado.getJMSMessageID().equals(recibidoA.getJMSMessageID())
                    || !enviado.getJMSMessageID().equals(recibidoB.getJMSMessageID())) {
                    throw new IllegalStateException("No se verifico la difusion del mismo mensaje a ambas suscripciones");
                }
                System.out.println("VERIFICACION CORRECTA: dos suscripciones independientes recibieron el mismo mensaje.");
                System.out.println("JMSMessageID: " + enviado.getJMSMessageID());
                System.out.println("No prueba envio de correos, integracion con SGRA ni recuperacion ante fallos.");
            }
        }
    }

    private static String json(long solicitud, String evento) {
        return "{\"eventoId\":\"" + evento + "\",\"tipo\":\"SolicitudTutoriaCreada\",\"solicitudId\":" + solicitud + "}";
    }

    private static String env(String name, String fallback) {
        return System.getenv().getOrDefault(name, fallback);
    }

    private static void imprimir(String rol, String nombre) {
        System.out.println("\nPRACTICA JMS - NIXON PIGUAVE | " + rol + " | " + nombre);
        System.out.println("API: jakarta.jms | Proveedor: Apache Artemis | Topico: " + TOPICO);
        System.out.println("Conexion: " + env("JMS_URL", "tcp://localhost:61617"));
    }
}
