package ro.alexandru.wallet.domain.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class WalletOperationSuccess {

    private final WalletOperationType type;
    private final String walletId;
    private final BigDecimal balance;

    @JsonCreator
    public WalletOperationSuccess(@JsonProperty("type") WalletOperationType type,
                                  @JsonProperty("walletId") String walletId,
                                  @JsonProperty("balance") BigDecimal balance) {
        this.type = type;
        this.walletId = walletId;
        this.balance = balance;
    }

    public static WalletOperationSuccess from(WalletOperation walletOperation, BigDecimal balance) {
        return new WalletOperationSuccess(walletOperation.getType(), walletOperation.getWalletId(), balance);
    }

    public WalletOperationType getType() {
        return type;
    }

    public String getWalletId() {
        return walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "WalletOperationSuccess{" +
                "type=" + type +
                ", walletId='" + walletId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
