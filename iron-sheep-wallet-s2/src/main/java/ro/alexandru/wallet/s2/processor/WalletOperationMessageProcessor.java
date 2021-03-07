package ro.alexandru.wallet.s2.processor;

import ro.alexandru.wallet.domain.model.Wallet;
import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.domain.model.event.WalletOperationResult;
import ro.alexandru.wallet.domain.service.WalletOperationService;
import ro.alexandru.wallet.messaging.consumer.MessageProcessor;
import ro.alexandru.wallet.messaging.producer.MessageProducerException;
import ro.alexandru.wallet.s2.db.WalletRepository;
import ro.alexandru.wallet.s2.messaging.WalletOperationResultMessageProducer;
import ro.alexandru.wallet.s2.processor.exception.WalletOperationProcessingException;

import static ro.alexandru.wallet.domain.model.event.WalletOperationType.GET;

public class WalletOperationMessageProcessor implements MessageProcessor<WalletOperation> {

    private final WalletOperationService walletOperationService;
    private final WalletRepository walletRepository;

    public WalletOperationMessageProcessor() {
        this.walletOperationService = new WalletOperationService();
        this.walletRepository = new WalletRepository();
    }

    @Override
    public void process(WalletOperation walletOperation) {
        Wallet wallet = walletRepository.getWallet(walletOperation.getWalletId());
        WalletOperationResult result = walletOperationService.apply(wallet, walletOperation);

        if (result.isSuccess() && walletOperation.getType() != GET) {
            walletRepository.createOrUpdateWallet(new Wallet(result.getSuccess().getWalletId(), result.getSuccess().getBalance()));
        }
        sendWalletOperationResultEvent(result);
    }

    private void sendWalletOperationResultEvent(WalletOperationResult walletOperationResult) {
        try {
            WalletOperationResultMessageProducer.getInstance().send(walletOperationResult);
        } catch (MessageProducerException e) {
            throw new WalletOperationProcessingException("Error processing wallet operation of type " + walletOperationResult.getOperationType(), e);
        }
    }
}
