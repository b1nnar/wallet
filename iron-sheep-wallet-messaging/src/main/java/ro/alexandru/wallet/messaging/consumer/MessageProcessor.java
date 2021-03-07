package ro.alexandru.wallet.messaging.consumer;

@FunctionalInterface
public interface MessageProcessor<T> {

    void process(T value);
}
