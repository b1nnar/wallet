package ro.alexandru.wallet;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import ro.alexandru.wallet.s1.messaging.KafkaMessageProducer;
import ro.alexandru.wallet.s1.messaging.KafkaMessageProducerConfig;
import ro.alexandru.wallet.s1.messaging.MessageProducer;
import ro.alexandru.wallet.s1.messaging.MessageProducerException;

import java.net.URI;
import java.util.Properties;

public class App {

    public static void main(String[] args) {
        URI baseUri = URI.create("http://localhost:8080/wallet/");
        ResourceConfig config = new ResourceConfig()
                .packages("ro.alexandru.wallet");
        JdkHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("HTTP Server started. WADL location: " + baseUri + "application.wadl");
    }

    /*public static void main(String[] args) throws MessageProducerException {
        KafkaMessageProducerConfig kafkaMessageProducerConfig = new KafkaMessageProducerConfig("localhost:9092", "my-producer", "TEST.01");
        MessageProducer<String> messageProducer = new KafkaMessageProducer<>(kafkaMessageProducerConfig, KafkaMessageProducer.STRING_SERIALIZER);

        for (int i = 0; i < 100; i++) {
            messageProducer.send("test value " + i);
        }

        messageProducer.close();
    }*/
}