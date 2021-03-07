package ro.alexandru.wallet.messaging.consumer;

import java.util.Optional;

public interface MessageConsumer<T> {

    Optional<T> poll();

    void commit();

    void close();
}
