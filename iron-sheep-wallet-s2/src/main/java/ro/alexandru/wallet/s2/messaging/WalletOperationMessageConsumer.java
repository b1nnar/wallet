package ro.alexandru.wallet.s2.messaging;

import org.apache.kafka.common.serialization.Deserializer;
import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumer;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumerConfig;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.serializer.JSONDeserializer;

import static ro.alexandru.wallet.messaging.Messaging.BROKER_ADDRESS;
import static ro.alexandru.wallet.messaging.Messaging.S1_TOPIC;

public class WalletOperationMessageConsumer extends KafkaMessageConsumer<WalletOperation> {

    private static MessageConsumer<WalletOperation> messageConsumer = null;

    public WalletOperationMessageConsumer(KafkaMessageConsumerConfig config, Deserializer<WalletOperation> messageDeserializer) {
        super(config, messageDeserializer);
    }

    synchronized public static MessageConsumer<WalletOperation> getInstance() {
        if (messageConsumer == null) {
            messageConsumer = new KafkaMessageConsumer<>(new KafkaMessageConsumerConfig(
                    BROKER_ADDRESS, "s2-consumer", S1_TOPIC
            ), new JSONDeserializer<>(WalletOperation.class));
        }
        return messageConsumer;
    }
}