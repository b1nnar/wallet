package ro.alexandru.wallet.domain.model;

import java.math.BigDecimal;

public class Wallet {

    private String id;
    private BigDecimal balance;

    public Wallet() {
    }

    public Wallet(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}