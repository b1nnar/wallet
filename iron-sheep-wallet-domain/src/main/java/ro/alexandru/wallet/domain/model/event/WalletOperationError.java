package ro.alexandru.wallet.domain.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletOperationError {

    private WalletOperationErrorType type;
    private String error;

    @JsonCreator
    public WalletOperationError(@JsonProperty("type") WalletOperationErrorType type,
                                @JsonProperty("error") String error) {
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
