package ro.alexandru.wallet.s2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumer;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumerConfig;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.consumer.MessageConsumerProcess;
import ro.alexandru.wallet.messaging.consumer.MessageProcessor;
import ro.alexandru.wallet.messaging.serializer.JSONDeserializer;

public class S2App {

    private static final Logger LOG = LoggerFactory.getLogger(S2App.class);

    public static void main(String[] args) {
        createAndStartMessageConsumerProcess();
    }

    private static void createAndStartMessageConsumerProcess() {
        KafkaMessageConsumerConfig kafkaMessageConsumerConfig = new KafkaMessageConsumerConfig(
                "localhost:9092", "s2-consumer", "TOPIC.S1"
        );

        MessageConsumer<WalletOperation> messageConsumer = new KafkaMessageConsumer<>(kafkaMessageConsumerConfig, new JSONDeserializer<>(WalletOperation.class));
        MessageProcessor<WalletOperation> messageProcessor = value -> LOG.info("S2 Consumer processed value: `{}`", value);
        MessageConsumerProcess<WalletOperation> messageConsumerProcess = new MessageConsumerProcess<>("S2 Consumer", messageConsumer, messageProcessor);

        messageConsumerProcess.start();
    }
}