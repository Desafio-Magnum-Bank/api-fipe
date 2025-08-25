package desafio.config.rabbitmq;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@ApplicationScoped
public class QueueInitializer {

    @ConfigProperty(name = "mp.messaging.connector.smallrye-rabbitmq.host")
    String host;

    @ConfigProperty(name = "mp.messaging.connector.smallrye-rabbitmq.username")
    String username;

    @ConfigProperty(name = "mp.messaging.connector.smallrye-rabbitmq.password")
    String password;

    @ConfigProperty(name = "mp.messaging.connector.smallrye-rabbitmq.port")
    int port;

    public void onStart(@Observes StartupEvent ev) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);

            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {

                channel.queueDeclare("brand-queue", true, false, false, null);

                channel.queueDeclare("brand-dlq", true, false, false, null);

                channel.exchangeDeclare("brand-exchange", "direct", true);

                channel.queueBind("brand-queue", "brand-exchange", "brand-routing-key");
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar filas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}