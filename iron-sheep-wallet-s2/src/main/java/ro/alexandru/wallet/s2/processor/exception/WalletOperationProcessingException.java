package ro.alexandru.wallet.s2.processor.exception;

public class WalletOperationProcessingException extends RuntimeException {

    public WalletOperationProcessingException(String message) {
        super(message);
    }

    public WalletOperationProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
