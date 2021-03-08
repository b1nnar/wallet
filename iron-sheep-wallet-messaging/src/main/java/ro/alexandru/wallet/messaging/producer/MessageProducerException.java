package ro.alexandru.wallet.messaging.producer;

public class MessageProducerException extends RuntimeException {

    public MessageProducerException(String message) {
        super(message);
    }

    public MessageProducerException(String message, Throwable cause) {
        super(message, cause);
    }
}
