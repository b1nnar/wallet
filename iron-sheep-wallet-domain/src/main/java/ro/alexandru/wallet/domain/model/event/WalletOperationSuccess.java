package ro.alexandru.wallet.domain.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class WalletOperationSuccess {

    private WalletOperationType type;
    private String walletId;
    private BigDecimal balance;

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

    public void setType(WalletOperationType type) {
        this.type = type;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
