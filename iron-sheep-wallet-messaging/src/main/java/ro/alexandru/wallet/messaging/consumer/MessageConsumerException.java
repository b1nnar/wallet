package ro.alexandru.wallet.messaging.consumer;

public class MessageConsumerException extends RuntimeException {

    public MessageConsumerException(String message) {
        super(message);
    }

    public MessageConsumerException(String message, Throwable cause) {
        super(message, cause);
    }
}
