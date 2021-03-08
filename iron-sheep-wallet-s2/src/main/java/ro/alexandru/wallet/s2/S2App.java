package ro.alexandru.wallet.s2;

import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.messaging.consumer.MessageConsumer;
import ro.alexandru.wallet.messaging.consumer.MessageConsumerProcess;
import ro.alexandru.wallet.messaging.consumer.MessageProcessor;
import ro.alexandru.wallet.s2.messaging.WalletOperationMessageConsumer;
import ro.alexandru.wallet.s2.processor.WalletOperationMessageProcessor;

public class S2App {

    public static void main(String[] args) {
        createAndStartMessageConsumerProcess();
    }

    private static void createAndStartMessageConsumerProcess() {
        MessageConsumer<WalletOperation> messageConsumer = WalletOperationMessageConsumer.getInstance();
        MessageProcessor<WalletOperation> messageProcessor = new WalletOperationMessageProcessor();
        MessageConsumerProcess<WalletOperation> messageConsumerProcess = new MessageConsumerProcess<>("S2 Consumer", messageConsumer, messageProcessor);

        messageConsumerProcess.start();
    }
}