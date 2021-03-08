package ro.alexandru.wallet.s2;

import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumer;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumerConfig;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.consumer.MessageConsumerProcess;
import ro.alexandru.wallet.messaging.consumer.MessageProcessor;
import ro.alexandru.wallet.messaging.serializer.JSONDeserializer;
import ro.alexandru.wallet.s2.processor.WalletOperationMessageProcessor;

import static ro.alexandru.wallet.messaging.Topics.S1_TOPIC;

public class S2App {

    public static void main(String[] args) {
        createAndStartMessageConsumerProcess();
    }

    private static void createAndStartMessageConsumerProcess() {
        KafkaMessageConsumerConfig kafkaMessageConsumerConfig = new KafkaMessageConsumerConfig(
                "localhost:9092", "s2-consumer", S1_TOPIC
        );

        MessageConsumer<WalletOperation> messageConsumer = new KafkaMessageConsumer<>(kafkaMessageConsumerConfig, new JSONDeserializer<>(WalletOperation.class));
        MessageProcessor<WalletOperation> messageProcessor = new WalletOperationMessageProcessor();
        MessageConsumerProcess<WalletOperation> messageConsumerProcess = new MessageConsumerProcess<>("S2 Consumer", messageConsumer, messageProcessor);

        messageConsumerProcess.start();
    }
}