package ro.alexandru.wallet.messaging.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.StreamSupport;

public class KafkaMessageConsumer<T> implements MessageConsumer<T> {

    public static final Deserializer<String> STRING_DESERIALIZER = new StringDeserializer();
    private static final Duration POLL_DURATION = Duration.ofMillis(1000);

    private final Consumer<String, T> kafkaConsumer;
    private final String consumerGroupId;
    private final String topic;

    public KafkaMessageConsumer(KafkaMessageConsumerConfig config, Deserializer<T> messageDeserializer) {
        this.kafkaConsumer = createKafkaConsumer(config, messageDeserializer);
        this.consumerGroupId = config.getConsumerGroupId();
        this.topic = config.getTopic();
    }

    @Override
    public Optional<T> poll() {
        ConsumerRecords<String, T> records = kafkaConsumer.poll(POLL_DURATION);
        if (records.count() > 1) {
            throw new MessageConsumerException(
                    String.format("Consumer with id %s from topic %s consumed more than 1 message", consumerGroupId, topic));
        }
        return StreamSupport.stream(records.records(topic).spliterator(), false)
                .findFirst()
                .map(ConsumerRecord::value);
    }

    @Override
    public void commit() {
        kafkaConsumer.commitSync();
    }

    @Override
    public void close() {
        kafkaConsumer.close();
    }

    private Consumer<String, T> createKafkaConsumer(KafkaMessageConsumerConfig config, Deserializer<T> messageDeserializer) {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBrokerAddress());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, config.getConsumerGroupId());
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Consumer<String, T> kafkaConsumer = new KafkaConsumer<>(consumerProperties, STRING_DESERIALIZER, messageDeserializer);
        kafkaConsumer.subscribe(Collections.singletonList(config.getTopic()));

        return kafkaConsumer;
    }
}
