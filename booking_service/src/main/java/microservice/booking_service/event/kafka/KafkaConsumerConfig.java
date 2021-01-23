package microservice.booking_service.event.kafka;

import microservice.booking_service.event.model.PaymentSucceededEvent;
import microservice.booking_service.event.model.PaymentSucceededEventDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/***
 * Configuration for Consuming kafka events.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka-bootstap-server}")
    private String kafkaBootstapServer;

    /***
     * Creates a factory configuration.
     * @return a factory configuration for Kafka consumer.
     */
    @Bean
    public ConsumerFactory<String, PaymentSucceededEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaBootstapServer);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "myGroup");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new PaymentSucceededEventDeserializer());
    }

    /***
     * Creates a container for our @KafkaListener we inject in our code elsewhere
     * @return a factory container for Kafka
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentSucceededEvent> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PaymentSucceededEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
