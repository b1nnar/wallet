package ro.alexandru.wallet.s2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumer;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumerConfig;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.consumer.MessageConsumerProcess;
import ro.alexandru.wallet.messaging.consumer.MessageProcessor;

import static ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumer.STRING_DESERIALIZER;

public class S2App {

    private static final Logger LOG = LoggerFactory.getLogger(S2App.class);

    public static void main(String[] args) {
        createAndStartMessageConsumerProcess();
    }

    private static void createAndStartMessageConsumerProcess() {
        KafkaMessageConsumerConfig kafkaMessageConsumerConfig = new KafkaMessageConsumerConfig(
                "localhost:9092", "s2-consumer", "TOPIC.S1"
        );

        MessageConsumer<String> messageConsumer = new KafkaMessageConsumer<>(kafkaMessageConsumerConfig, STRING_DESERIALIZER);
        MessageProcessor<String> messageProcessor = value -> LOG.info("S2 Consumer processed value: `{}`", value);
        MessageConsumerProcess<String> messageConsumerProcess = new MessageConsumerProcess<>("S2 Consumer", messageConsumer, messageProcessor);

        messageConsumerProcess.start();
    }
}