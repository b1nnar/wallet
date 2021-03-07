package ro.alexandru.wallet.s2.messaging;

import org.apache.kafka.common.serialization.Serializer;
import ro.alexandru.wallet.domain.model.event.WalletOperationResult;
import ro.alexandru.wallet.messaging.producer.KafkaMessageProducer;
import ro.alexandru.wallet.messaging.producer.KafkaMessageProducerConfig;
import ro.alexandru.wallet.messaging.producer.MessageProducer;
import ro.alexandru.wallet.messaging.serializer.JSONSerializer;

public class WalletOperationResultMessageProducer extends KafkaMessageProducer<WalletOperationResult> {

    private static MessageProducer<WalletOperationResult> messageProducer = null;

    public WalletOperationResultMessageProducer(KafkaMessageProducerConfig config, Serializer<WalletOperationResult> messageDeserializer) {
        super(config, messageDeserializer);
    }

    synchronized public static MessageProducer<WalletOperationResult> getInstance() {
        if (messageProducer == null) {
            messageProducer = new WalletOperationResultMessageProducer(
                    new KafkaMessageProducerConfig(
                            "localhost:9092", "s2-producer", "TOPIC.S2.01"
                    ),
                    new JSONSerializer<>()
            );
        }
        return messageProducer;
    }
}