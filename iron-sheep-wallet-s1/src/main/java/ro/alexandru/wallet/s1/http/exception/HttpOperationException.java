package ro.alexandru.wallet.s1.http.exception;

public class HttpOperationException extends RuntimeException {

    public HttpOperationException(String message) {
        super(message);
    }

    public HttpOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
