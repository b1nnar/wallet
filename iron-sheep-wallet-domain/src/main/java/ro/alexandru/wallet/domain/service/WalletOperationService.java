package ro.alexandru.wallet.domain.service;

import ro.alexandru.wallet.domain.model.Wallet;
import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.domain.model.event.WalletOperationError;
import ro.alexandru.wallet.domain.model.event.WalletOperationResult;
import ro.alexandru.wallet.domain.model.event.WalletOperationSuccess;

import java.math.BigDecimal;

import static ro.alexandru.wallet.domain.service.WalletOperationErrorCatalog.debitOperationExceedsBalance;
import static ro.alexandru.wallet.domain.service.WalletOperationErrorCatalog.negativeOperationAmount;
import static ro.alexandru.wallet.domain.service.WalletOperationErrorCatalog.unrecognizedWalletOperationType;
import static ro.alexandru.wallet.domain.service.WalletOperationErrorCatalog.walletNotFound;

public class WalletOperationService {

    public WalletOperationResult apply(Wallet wallet, WalletOperation walletOperation) {
        switch (walletOperation.getType()) {
            case GET:
                return get(walletOperation, wallet);
            case DEBIT:
                return debit(walletOperation, wallet);
            case CREDIT:
                return credit(walletOperation, wallet);
            default:
                return error(walletOperation, unrecognizedWalletOperationType(walletOperation));
        }
    }

    private WalletOperationResult get(WalletOperation walletOperation, Wallet wallet) {
        if (wallet == null) {
            return error(walletOperation, walletNotFound(walletOperation));
        }
        return success(walletOperation, wallet.getBalance());
    }

    private WalletOperationResult debit(WalletOperation walletOperation, Wallet wallet) {
        if (wallet == null) {
            return error(walletOperation, walletNotFound(walletOperation));
        }
        if (walletOperation.getAmount().signum() < 0) {
            return error(walletOperation, negativeOperationAmount(walletOperation));
        }
        if (wallet.getBalance().compareTo(walletOperation.getAmount()) < 0) {
            return error(walletOperation, debitOperationExceedsBalance(walletOperation, wallet.getBalance()));
        }
        return success(walletOperation, wallet.getBalance().subtract(walletOperation.getAmount()));
    }

    private WalletOperationResult credit(WalletOperation walletOperation, Wallet wallet) {
        if (walletOperation.getAmount().signum() < 0) {
            return error(walletOperation, negativeOperationAmount(walletOperation));
        }
        BigDecimal currentBalance = (wallet == null) ? BigDecimal.ZERO : wallet.getBalance();
        return success(walletOperation, currentBalance.add(walletOperation.getAmount()));
    }

    private WalletOperationResult success(WalletOperation walletOperation, BigDecimal balance) {
        return WalletOperationResult.success(walletOperation.getType(), new WalletOperationSuccess(walletOperation.getWalletId(), balance));
    }

    private WalletOperationResult error(WalletOperation walletOperation, WalletOperationError walletOperationError) {
        return WalletOperationResult.error(walletOperation.getType(), walletOperationError);
    }
}
