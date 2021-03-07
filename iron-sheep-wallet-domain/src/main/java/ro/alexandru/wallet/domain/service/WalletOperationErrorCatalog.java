package ro.alexandru.wallet.domain.service;

import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.domain.model.event.WalletOperationError;
import ro.alexandru.wallet.domain.model.event.WalletOperationErrorType;

import java.math.BigDecimal;

public class WalletOperationErrorCatalog {

    public static WalletOperationError negativeOperationAmount(WalletOperation walletOperation) {
        return new WalletOperationError(WalletOperationErrorType.NEGATIVE_OPERATION_AMOUNT,
                String.format("Amount to `%s` for wallet with id `%s` is negative `%s`",
                        walletOperation.getType(), walletOperation.getWalletId(), walletOperation.getAmount().toString()));
    }

    public static WalletOperationError unrecognizedWalletOperationType(WalletOperation walletOperation) {
        return new WalletOperationError(WalletOperationErrorType.UNRECOGNIZED_OPERATION_TYPE,
                String.format("Unrecognized wallet operation `%s` for wallet with id `%s`", walletOperation.getType(), walletOperation.getWalletId()));
    }

    public static WalletOperationError debitOperationExceedsBalance(WalletOperation walletOperation, BigDecimal balance) {
        return new WalletOperationError(WalletOperationErrorType.DEBIT_OPERATION_EXCEEDS_BALANCE,
                String.format("Debit of `%s` from wallet with id `%s` exceeds current balance `%s`",
                        walletOperation.getAmount().toString(), walletOperation.getWalletId(), balance.toString()));
    }

    public static WalletOperationError walletNotFound(WalletOperation walletOperation) {
        return new WalletOperationError(WalletOperationErrorType.WALLET_NOT_FOUND,
                String.format("Wallet with id `%s` was not found", walletOperation.getWalletId()));
    }
}
