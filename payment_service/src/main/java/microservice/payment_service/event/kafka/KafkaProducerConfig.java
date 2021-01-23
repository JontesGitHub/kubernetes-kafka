package microservice.payment_service.event.kafka;

import microservice.payment_service.event.model.Event;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/***
 * Configuration for producing kafka events.
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${kafka-bootstap-server}")
    private String kafkaBootstapServer;

    /***
     * Creates a factory configuration.
     * @return a factory configuration for Kafka consumer.
     */
    @Bean
    public ProducerFactory<String, Event> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaBootstapServer);
        JsonSerializer<Event> jsonSerializer = new JsonSerializer<>();
        jsonSerializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), jsonSerializer);
    }

    /***
     * Creates a KafkaTemplate Bean we can inject in our code elsewhere
     * @return a Kafkatemplate Bean
     */
    @Bean
    public KafkaTemplate<String, Event> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
