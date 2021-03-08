package ro.alexandru.wallet.messaging;

import org.awaitility.Awaitility;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.producer.MessageProducer;

import java.time.Duration;
import java.util.Optional;

/**
 * Produces a message and waits for consumer to receive a response, with a timeout.
 * sendAndReceive operation is synchronized so that only 1 thread at a time can call it.
 *
 * @param <U>
 * @param <V>
 */
public class BlockingMessageBroker<U, V> {

    private final MessageProducer<U> producer;
    private final MessageConsumer<V> consumer;

    public BlockingMessageBroker(MessageProducer<U> producer, MessageConsumer<V> consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    synchronized public V sendAndReceive(U message) {
        producer.send(message);
        V v = Awaitility.await()
                .timeout(Duration.ofSeconds(30))
                .pollInterval(Duration.ofMillis(500))
                .until(consumer::poll, Optional::isPresent)
                .get();
        consumer.commit();
        return v;
    }
}
