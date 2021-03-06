package ro.alexandru.wallet.domain.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletOperationError {

    private WalletOperationErrorType type;
    private String error;

    public WalletOperationError() {
    }

    @JsonCreator
    public WalletOperationError(@JsonProperty("type") WalletOperationErrorType type,
                                @JsonProperty("error") String error) {
        this.type = type;
        this.error = error;
    }

    public WalletOperationErrorType getType() {
        return type;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "WalletOperationError{" +
                "type=" + type +
                ", error='" + error + '\'' +
                '}';
    }
}
