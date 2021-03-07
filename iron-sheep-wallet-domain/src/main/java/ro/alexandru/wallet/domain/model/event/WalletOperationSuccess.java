package ro.alexandru.wallet.domain.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class WalletOperationSuccess {

    private final String walletId;
    private final BigDecimal balance;

    @JsonCreator
    public WalletOperationSuccess(@JsonProperty("walletId") String walletId,
                                  @JsonProperty("newBalance") BigDecimal balance) {
        this.walletId = walletId;
        this.balance = balance;
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
                "walletId='" + walletId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
