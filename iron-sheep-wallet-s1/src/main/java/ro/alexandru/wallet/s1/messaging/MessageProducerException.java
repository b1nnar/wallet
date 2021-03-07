package ro.alexandru.wallet.s1.messaging;

public class MessageProducerException extends Exception {

    public MessageProducerException(String message) {
        super(message);
    }

    public MessageProducerException(String message, Throwable cause) {
        super(message, cause);
    }
}
