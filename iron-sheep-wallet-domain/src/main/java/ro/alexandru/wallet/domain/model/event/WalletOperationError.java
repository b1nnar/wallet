package ro.alexandru.wallet.domain.model.event;

public class WalletOperationError {

    private WalletOperationErrorType type;
    private String error;

    public WalletOperationError(WalletOperationErrorType type, String error) {
        this.type = type;
        this.error = error;
    }

    public static WalletOperationError from(WalletOperationErrorType type, String error) {
        return new WalletOperationError(type, error);
    }

    public WalletOperationErrorType getType() {
        return type;
    }

    public void setType(WalletOperationErrorType type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
