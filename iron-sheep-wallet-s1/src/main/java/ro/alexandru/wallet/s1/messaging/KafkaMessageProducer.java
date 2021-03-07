package ro.alexandru.wallet.s1.messaging;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.alexandru.wallet.WalletBalanceResource;

import java.util.Properties;
import java.util.UUID;

public class KafkaMessageProducer<T> implements MessageProducer<T> {

    public static final Serializer<String> STRING_SERIALIZER = new StringSerializer();
    private static final Logger LOG = LoggerFactory.getLogger(WalletBalanceResource.class);
    private final Producer<String, T> kafkaProducer;
    private final String topic;
    private final String messageKey;

    public KafkaMessageProducer(KafkaMessageProducerConfig config, Serializer<T> messageSerializer) {
        this.kafkaProducer = createKafkaProducer(config, messageSerializer);
        this.topic = config.getTopic();
        this.messageKey = generateKey();
    }

    @Override
    public void send(T value) throws MessageProducerException {
        LOG.debug("Trying to send message to topic `{}`: `{}:{}`", topic, messageKey, value);

        try {
            kafkaProducer.send(new ProducerRecord<>(topic, messageKey, value)).get();
        } catch (Exception e) {
            throw new MessageProducerException("Failure sending message", e);
        }
        LOG.info("Successfully sent message to topic `{}`: `{}:{}`", topic, messageKey, value);
    }

    @Override
    public void close() {
        kafkaProducer.close();
    }

    private Producer<String, T> createKafkaProducer(KafkaMessageProducerConfig config, Serializer<T> messageDeserializer) {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBrokerAddress());
        producerProperties.put(ProducerConfig.CLIENT_ID_CONFIG, config.getClientId());
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1);
        producerProperties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        producerProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

        return new KafkaProducer<>(producerProperties, STRING_SERIALIZER, messageDeserializer);
    }

    private String generateKey() {
        return UUID.randomUUID().toString();
    }
}
