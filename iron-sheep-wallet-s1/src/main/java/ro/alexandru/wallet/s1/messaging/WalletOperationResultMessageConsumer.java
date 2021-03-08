package ro.alexandru.wallet.s1.messaging;

import org.apache.kafka.common.serialization.Deserializer;
import ro.alexandru.wallet.domain.model.event.WalletOperationResult;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumer;
import ro.alexandru.wallet.messaging.consumer.KafkaMessageConsumerConfig;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.serializer.JSONDeserializer;

import static ro.alexandru.wallet.messaging.Messaging.BROKER_ADDRESS;
import static ro.alexandru.wallet.messaging.Messaging.S2_TOPIC;

public class WalletOperationResultMessageConsumer extends KafkaMessageConsumer<WalletOperationResult> {

    private static MessageConsumer<WalletOperationResult> messageConsumer = null;

    public WalletOperationResultMessageConsumer(KafkaMessageConsumerConfig config, Deserializer<WalletOperationResult> messageDeserializer) {
        super(config, messageDeserializer);
    }

    synchronized public static MessageConsumer<WalletOperationResult> getInstance() {
        if (messageConsumer == null) {
            messageConsumer = new KafkaMessageConsumer<>(new KafkaMessageConsumerConfig(
                    BROKER_ADDRESS, "s1-consumer", S2_TOPIC
            ), new JSONDeserializer<>(WalletOperationResult.class));
        }
        return messageConsumer;
    }
}