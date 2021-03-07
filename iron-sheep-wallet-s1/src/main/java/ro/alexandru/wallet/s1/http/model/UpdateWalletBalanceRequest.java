package ro.alexandru.wallet.s1.http.model;

import java.math.BigDecimal;

public class UpdateWalletBalanceRequest {

    private String id;
    private BigDecimal amount;

    public UpdateWalletBalanceRequest() {
    }

    public UpdateWalletBalanceRequest(String id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "UpdateWalletBalanceRequest{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                '}';
    }
}
