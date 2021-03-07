package ro.alexandru.wallet.domain.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletOperationResult {

    private final WalletOperationType operationType;
    private final WalletOperationError error;
    private final WalletOperationSuccess success;

    @JsonCreator
    public WalletOperationResult(
            @JsonProperty("operationType") WalletOperationType operationType,
            @JsonProperty("error") WalletOperationError error,
            @JsonProperty("success") WalletOperationSuccess success) {
        validateMutuallyExclusive(error, success);
        this.operationType = operationType;
        this.error = error;
        this.success = success;
    }

    public static WalletOperationResult success(WalletOperationType operationType, WalletOperationSuccess success) {
        return new WalletOperationResult(operationType, null, success);
    }

    public static WalletOperationResult error(WalletOperationType operationType, WalletOperationError error) {
        return new WalletOperationResult(operationType, error, null);
    }

    public WalletOperationType getOperationType() {
        return operationType;
    }

    public WalletOperationError getError() {
        return error;
    }

    public WalletOperationSuccess getSuccess() {
        return success;
    }

    @JsonIgnore
    public boolean isError() {
        return error != null;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return !isError();
    }

    private void validateMutuallyExclusive(WalletOperationError error, WalletOperationSuccess success) {
        boolean valid = (error == null || success == null) &&
                (error != null || success != null);
        if(!valid) {
            throw new IllegalArgumentException("success and error cannot both be non null or both null");
        }
    }

    @Override
    public String toString() {
        return "WalletOperationResult{" +
                "operationType=" + operationType +
                ", error=" + error +
                ", success=" + success +
                '}';
    }
}
