package microservice.payment_service.event.kafka;

import microservice.payment_service.event.model.BookingCanceledEvent;
import microservice.payment_service.event.model.BookingCanceledEventDeserializer;
import microservice.payment_service.event.model.Event;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka-bootstap-server}")
    private String kafkaBootstapServer;

    @Bean
    public ConsumerFactory<String, BookingCanceledEvent> consumerFactory() {
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

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new BookingCanceledEventDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingCanceledEvent> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, BookingCanceledEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
