package ro.alexandru.wallet.s1;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumer;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumerConfig;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.consumer.MessageConsumerProcess;
import ro.alexandru.wallet.messaging.consumer.MessageProcessor;

import java.net.URI;

import static ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumer.STRING_DESERIALIZER;

public class S1App {

    private static final Logger LOG = LoggerFactory.getLogger(S1App.class);

    public static void main(String[] args) {
        createAndStartHttpServer();
        createAndStartMessageConsumerProcess();
    }

    private static void createAndStartHttpServer() {
        URI baseUri = URI.create("http://localhost:8080/wallet/");
        ResourceConfig config = new ResourceConfig().packages("ro.alexandru.wallet.rest");
        JdkHttpServerFactory.createHttpServer(baseUri, config);
        LOG.info("S1 HTTP Server started. WADL location: `{}application.wadl`", baseUri);
    }

    private static void createAndStartMessageConsumerProcess() {
        KafkaMessageConsumerConfig kafkaMessageConsumerConfig = new KafkaMessageConsumerConfig(
                "localhost:9092", "s1-consumer", "TOPIC.S2"
        );

        MessageConsumer<String> messageConsumer = new KafkaMessageConsumer<>(kafkaMessageConsumerConfig, STRING_DESERIALIZER);
        MessageProcessor<String> messageProcessor = value -> LOG.info("S1 Consumer processed value: `{}`", value);
        MessageConsumerProcess<String> messageConsumerProcess = new MessageConsumerProcess<>("S1 Consumer", messageConsumer, messageProcessor);

        messageConsumerProcess.start();
    }
}
