package ro.alexandru.wallet.s1.messaging;

import org.apache.kafka.common.serialization.Serializer;
import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.messaging.producer.KafkaMessageProducer;
import ro.alexandru.wallet.messaging.producer.KafkaMessageProducerConfig;
import ro.alexandru.wallet.messaging.producer.MessageProducer;
import ro.alexandru.wallet.messaging.serializer.JSONSerializer;

public class WalletOperationMessageProducer extends KafkaMessageProducer<WalletOperation> {

    private static MessageProducer<WalletOperation> messageProducer = null;

    public WalletOperationMessageProducer(KafkaMessageProducerConfig config, Serializer<WalletOperation> messageDeserializer) {
        super(config, messageDeserializer);
    }

    synchronized public static MessageProducer<WalletOperation> getInstance() {
        if (messageProducer == null) {
            messageProducer = new WalletOperationMessageProducer(
                    new KafkaMessageProducerConfig(
                            "localhost:9092", "s1-producer", "TOPIC.S1.01"
                    ),
                    new JSONSerializer<>()
            );
        }
        return messageProducer;
    }
}