package ec.uteq.rtv;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;
import javax.naming.Context;
import javax.naming.InitialContext;

public final class PracticaJmsRtv {
    private static final String TOPICO = "rtv.inspecciones.iniciada";
    private static final ZoneId ECUADOR = ZoneId.of("America/Guayaquil");

    public static void main(String[] args) {
        try {
            if (args.length == 0) throw new IllegalArgumentException("Usar: suscribir auditoria|indicadores | publicar 42 evt-rtv-001 | verificar");
            switch (args[0]) {
                case "suscribir" -> {
                    if (args.length != 2 || !Set.of("auditoria", "indicadores").contains(args[1])) {
                        throw new IllegalArgumentException("Usar: suscribir auditoria|indicadores");
                    }
                    suscribir(factory(), args[1]);
                }
                case "publicar" -> {
                    if (args.length != 3 || !args[2].matches("[A-Za-z0-9_-]{1,80}")) {
                        throw new IllegalArgumentException("Usar: publicar 42 evt-rtv-001 (eventoId alfanumerico)");
                    }
                    long turnoId = Long.parseLong(args[1]);
                    if (turnoId <= 0) throw new IllegalArgumentException("turnoId debe ser positivo");
                    publicar(factory(), turnoId, args[2]);
                }
                case "verificar" -> verificar(factory());
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
        config.put("connectionFactory.ConnectionFactory", env("JMS_URL", "tcp://localhost:61618"));
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
            context.setClientID("rtv-" + nombre + "-" + UUID.randomUUID());
            Topic topic = context.createTopic(TOPICO);
            try (JMSConsumer consumer = context.createConsumer(topic)) {
                imprimir("SUSCRIPTOR", nombre);
                System.out.println("Suscripcion independiente NO DURABLE. Esperando eventos... Ctrl+C para salir.");
                while (true) {
                    TextMessage message = (TextMessage) consumer.receive();
                    System.out.println("\nRECIBIDO por " + nombre + " | " + ahora());
                    System.out.println("JMSMessageID: " + message.getJMSMessageID());
                    System.out.println("JMSRedelivered: " + message.getJMSRedelivered());
                    System.out.println("Mensaje: " + message.getText());
                }
            }
        }
    }

    private static void publicar(ConnectionFactory factory, long turnoId, String eventoId) throws Exception {
        try (JMSContext context = conectar(factory)) {
            Topic topic = context.createTopic(TOPICO);
            TextMessage message = context.createTextMessage(json(turnoId, eventoId));
            message.setStringProperty("eventoId", eventoId);
            context.createProducer().setDeliveryMode(DeliveryMode.PERSISTENT).send(topic, message);
            imprimir("PRODUCTOR", "inicio ficticio de inspeccion");
            System.out.println("PUBLICADO | " + ahora());
            System.out.println("JMSMessageID: " + message.getJMSMessageID());
            System.out.println("Mensaje: " + message.getText());
            System.out.println("El productor simula un inicio ya registrado. No consulta ni modifica el RTV.");
        }
    }

    private static void verificar(ConnectionFactory factory) throws Exception {
        String unique = UUID.randomUUID().toString();
        String cuerpo = json(999, "verificacion-" + unique);
        try (JMSContext a = conectar(factory); JMSContext b = conectar(factory); JMSContext p = conectar(factory)) {
            Topic topic = p.createTopic("rtv.verificacion." + unique);
            try (JMSConsumer ca = a.createConsumer(topic); JMSConsumer cb = b.createConsumer(topic)) {
                TextMessage enviado = p.createTextMessage(cuerpo);
                p.createProducer().send(topic, enviado);
                TextMessage recibidoA = (TextMessage) ca.receive(10000);
                TextMessage recibidoB = (TextMessage) cb.receive(10000);
                if (recibidoA == null || recibidoB == null
                    || !cuerpo.equals(recibidoA.getText()) || !cuerpo.equals(recibidoB.getText())
                    || !enviado.getJMSMessageID().equals(recibidoA.getJMSMessageID())
                    || !enviado.getJMSMessageID().equals(recibidoB.getJMSMessageID())) {
                    throw new IllegalStateException("No se verifico la entrega del mismo mensaje a las dos suscripciones");
                }
                System.out.println("VERIFICACION CORRECTA: dos suscripciones recibieron el mismo cuerpo y JMSMessageID.");
                System.out.println("Fecha Ecuador: " + ahora());
                System.out.println("JMSMessageID: " + enviado.getJMSMessageID());
                System.out.println("Prueba de distribucion JMS; sin integracion con los servicios del RTV.");
            }
        }
    }

    private static String json(long turnoId, String eventoId) {
        return "{\"eventoId\":\"" + eventoId + "\",\"tipo\":\"InspeccionIniciada\",\"inspeccionId\":\""
            + UUID.randomUUID() + "\",\"turnoId\":" + turnoId + ",\"autorizacionId\":\"" + UUID.randomUUID()
            + "\",\"ocurridoEn\":\"" + ahora() + "\",\"version\":1}";
    }

    private static String env(String name, String fallback) {
        return System.getenv().getOrDefault(name, fallback);
    }

    private static String ahora() {
        return OffsetDateTime.now(ECUADOR).toString();
    }

    private static void imprimir(String rol, String nombre) {
        System.out.println("\nPRACTICA JMS RTV | " + rol + " | " + nombre);
        System.out.println("API: jakarta.jms | Proveedor: Apache Artemis 2.57.0");
        System.out.println("Topico: " + TOPICO + " | Conexion: " + env("JMS_URL", "tcp://localhost:61618"));
        System.out.println("Fecha Ecuador: " + ahora());
    }
}
