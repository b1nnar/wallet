package ro.alexandru.wallet.s1;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.domain.model.event.WalletOperationType;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumer;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumerConfig;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.consumer.MessageConsumerProcess;
import ro.alexandru.wallet.messaging.consumer.MessageProcessor;
import ro.alexandru.wallet.messaging.producer.KafkaMessageProducer;
import ro.alexandru.wallet.messaging.producer.KafkaMessageProducerConfig;
import ro.alexandru.wallet.messaging.producer.MessageProducer;
import ro.alexandru.wallet.messaging.producer.MessageProducerException;
import ro.alexandru.wallet.messaging.serializer.JSONDeserializer;
import ro.alexandru.wallet.messaging.serializer.JSONSerializer;

import java.math.BigDecimal;
import java.net.URI;

public class S1App {

    private static final Logger LOG = LoggerFactory.getLogger(S1App.class);

    public static void main(String[] args) throws MessageProducerException {
        createAndStartHttpServer();
        createAndStartMessageConsumerProcess();

        KafkaMessageProducerConfig kafkaMessageProducerConfig = new KafkaMessageProducerConfig("localhost:9092", "my-producer", "TOPIC.S2");
        MessageProducer<WalletOperation> messageProducer =
                new KafkaMessageProducer<>(kafkaMessageProducerConfig, new JSONSerializer<>());

        messageProducer.send(new WalletOperation(WalletOperationType.CREDIT, "alex", BigDecimal.TEN));

        messageProducer.close();
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

        MessageConsumer<WalletOperation> messageConsumer = new KafkaMessageConsumer<>(kafkaMessageConsumerConfig, new JSONDeserializer<>(WalletOperation.class));
        MessageProcessor<WalletOperation> messageProcessor = value -> LOG.info("S1 Consumer processed value: `{}`", value);
        MessageConsumerProcess<WalletOperation> messageConsumerProcess = new MessageConsumerProcess<>("S1 Consumer", messageConsumer, messageProcessor);

        messageConsumerProcess.start();
    }
}
