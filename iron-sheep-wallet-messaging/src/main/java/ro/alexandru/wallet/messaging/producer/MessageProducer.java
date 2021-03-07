package ro.alexandru.wallet.messaging.producer;

public interface MessageProducer<T> {

    void send(T value) throws MessageProducerException;

    void close();
}
