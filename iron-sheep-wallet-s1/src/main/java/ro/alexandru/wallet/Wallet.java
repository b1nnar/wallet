package ro.alexandru.wallet;

import java.math.BigDecimal;

public class Wallet {

    private final String id;
    private final String name;
    private final BigDecimal balance;

    public Wallet(String id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}