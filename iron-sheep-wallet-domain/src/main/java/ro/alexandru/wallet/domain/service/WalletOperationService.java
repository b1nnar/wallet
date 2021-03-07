package ro.alexandru.wallet.domain.service;

import io.vavr.control.Validation;
import ro.alexandru.wallet.domain.model.Wallet;
import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.domain.model.event.WalletOperationError;
import ro.alexandru.wallet.domain.model.event.WalletOperationSuccess;

import java.math.BigDecimal;

import static ro.alexandru.wallet.domain.service.WalletOperationErrorCatalog.debitOperationExceedsBalance;
import static ro.alexandru.wallet.domain.service.WalletOperationErrorCatalog.negativeOperationAmount;
import static ro.alexandru.wallet.domain.service.WalletOperationErrorCatalog.unrecognizedWalletOperationType;

public class WalletOperationService {

    public Validation<WalletOperationError, WalletOperationSuccess> apply(Wallet wallet, WalletOperation walletOperation) {
        if (walletOperation.getAmount().signum() < 0) {
            return Validation.invalid(negativeOperationAmount(walletOperation));
        }
        switch (walletOperation.getType()) {
            case DEBIT:
                return debit(walletOperation, wallet.getBalance());
            case CREDIT:
                return credit(walletOperation, wallet.getBalance());
            default:
                return Validation.invalid(unrecognizedWalletOperationType(walletOperation));
        }
    }

    private Validation<WalletOperationError, WalletOperationSuccess> debit(WalletOperation walletOperation, BigDecimal currentBalance) {
        if (currentBalance.compareTo(walletOperation.getAmount()) < 0) {
            return Validation.invalid(debitOperationExceedsBalance(walletOperation, currentBalance));
        }
        return valid(walletOperation, currentBalance.subtract(walletOperation.getAmount()));
    }

    private Validation<WalletOperationError, WalletOperationSuccess> credit(WalletOperation walletOperation, BigDecimal currentBalance) {
        return valid(walletOperation, currentBalance.add(walletOperation.getAmount()));
    }

    private Validation<WalletOperationError, WalletOperationSuccess> valid(WalletOperation walletOperation, BigDecimal newBalance) {
        return Validation.valid(WalletOperationSuccess.from(walletOperation, newBalance));
    }
}
