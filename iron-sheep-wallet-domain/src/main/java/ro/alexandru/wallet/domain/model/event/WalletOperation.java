package ro.alexandru.wallet.domain.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class WalletOperation {

    private final WalletOperationType type;
    private final String walletId;
    private final BigDecimal amount;

    @JsonCreator
    public WalletOperation(@JsonProperty("type") WalletOperationType type,
                           @JsonProperty("walletId") String walletId,
                           @JsonProperty("amount") BigDecimal amount) {
        this.type = type;
        this.walletId = walletId;
        this.amount = amount;
    }

    public WalletOperationType getType() {
        return type;
    }

    public String getWalletId() {
        return walletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "WalletOperation{" +
                "type=" + type +
                ", walletId='" + walletId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
