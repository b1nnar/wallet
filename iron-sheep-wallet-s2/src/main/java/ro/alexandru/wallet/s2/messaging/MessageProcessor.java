package ro.alexandru.wallet.s2.messaging;

@FunctionalInterface
public interface MessageProcessor<T> {

    void process(T value);
}
