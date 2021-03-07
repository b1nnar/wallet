package ro.alexandru.wallet.domain.model.event;

import java.math.BigDecimal;

public class WalletOperation {

    private WalletOperationType type;
    private String walletId;
    private BigDecimal amount;

    public WalletOperation(WalletOperationType type, String walletId, BigDecimal amount) {
        this.type = type;
        this.walletId = walletId;
        this.amount = amount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
