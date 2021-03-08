package ro.alexandru.wallet.s1.messaging;

import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.domain.model.event.WalletOperationResult;
import ro.alexandru.wallet.messaging.BlockingMessageBroker;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.producer.MessageProducer;

public class WalletOperationMessageBroker extends BlockingMessageBroker<WalletOperation, WalletOperationResult> {

    private static BlockingMessageBroker<WalletOperation, WalletOperationResult> messageBroker = null;

    public WalletOperationMessageBroker(MessageProducer<WalletOperation> producer, MessageConsumer<WalletOperationResult> consumer) {
        super(producer, consumer);
    }

    synchronized public static BlockingMessageBroker<WalletOperation, WalletOperationResult> getInstance() {
        if (messageBroker == null) {
            messageBroker = new WalletOperationMessageBroker(WalletOperationMessageProducer.getInstance(), WalletOperationResultMessageConsumer.getInstance());
        }
        return messageBroker;
    }
}