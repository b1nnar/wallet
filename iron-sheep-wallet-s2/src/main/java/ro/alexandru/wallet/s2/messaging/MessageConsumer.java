package ro.alexandru.wallet.s2.messaging;

import java.util.Optional;

public interface MessageConsumer<T> {

    Optional<T> poll();

    void commit();

    void close();
}
