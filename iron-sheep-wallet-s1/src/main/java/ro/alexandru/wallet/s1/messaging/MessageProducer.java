package ro.alexandru.wallet.s1.messaging;

public interface MessageProducer<T> {

    void send(T value) throws MessageProducerException;

    void close();
}
