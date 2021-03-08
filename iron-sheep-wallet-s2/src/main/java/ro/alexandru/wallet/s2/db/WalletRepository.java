package ro.alexandru.wallet.s2.db;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ro.alexandru.wallet.domain.model.Wallet;

import java.util.List;

public class WalletRepository {

    private static final Sql2o SQL_DB = new Sql2o("jdbc:postgresql://localhost:5434/postgres", "postgres", "postgres");

    public Wallet getWallet(String id) {
        List<Wallet> wallets;
        try (Connection con = SQL_DB.open()) {
            wallets = con.createQuery("SELECT * FROM wallet WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetch(Wallet.class);
        }
        if (wallets.isEmpty()) {
            return null;
        }
        return wallets.get(0);
    }

    public void createOrUpdateWallet(Wallet wallet) {
        Wallet dbWallet = getWallet(wallet.getId());
        if (dbWallet == null) {
            createWallet(wallet);
        } else {
            updateWallet(wallet);
        }
    }

    private void createWallet(Wallet wallet) {
        try (Connection con = SQL_DB.open()) {
            con.createQuery("INSERT INTO wallet(id, balance) VALUES (:id, :balance)")
                    .addParameter("id", wallet.getId())
                    .addParameter("balance", wallet.getBalance())
                    .executeUpdate();
        }
    }

    private void updateWallet(Wallet wallet) {
        try (Connection con = SQL_DB.open()) {
            con.createQuery("UPDATE wallet SET balance = :balance WHERE id = :id")
                    .addParameter("id", wallet.getId())
                    .addParameter("balance", wallet.getBalance())
                    .executeUpdate();
        }
    }
}
