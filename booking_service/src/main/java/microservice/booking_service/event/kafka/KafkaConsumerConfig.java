package microservice.booking_service.event.kafka;

import microservice.booking_service.event.model.Event;
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

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka-bootstap-server}")
    private String kafkaBootstapServer;

    @Bean
    public ConsumerFactory<String, PaymentSucceededEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaBootstapServer);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "myGroup");

//        Map<String, String> jsonProps = new HashMap<>();
//        jsonProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
//        jsonProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        JsonDeserializer<PaymentSucceededEvent> jsonDeserializer = new JsonDeserializer<>();
//        jsonDeserializer.configure(jsonProps, false);

//        jsonDeserializer.addTrustedPackages("*");
//        jsonDeserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new PaymentSucceededEventDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentSucceededEvent> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PaymentSucceededEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
