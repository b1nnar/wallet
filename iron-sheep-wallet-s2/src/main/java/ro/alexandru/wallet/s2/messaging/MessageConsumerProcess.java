package ro.alexandru.wallet.s2.messaging;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MessageConsumerProcess<T> {

    private final MessageConsumer<T> messageConsumer;
    private final MessageProcessor<T> messageProcessor;
    private final ScheduledExecutorService executor;
    private ScheduledFuture<?> scheduledFuture = null;

    public MessageConsumerProcess(MessageConsumer<T> messageConsumer, MessageProcessor<T> messageProcessor) {
        this.messageConsumer = messageConsumer;
        this.messageProcessor = messageProcessor;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        if (executor.isShutdown()) {
            throw new MessageConsumerException("Could not start message consumer process, the executor is shut down");
        }
        scheduledFuture = executor.scheduleAtFixedRate(this::consumeAndProcessNextMessage, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        if (scheduledFuture == null || executor.isShutdown()) {
            return;
        }
        scheduledFuture.cancel(true);
    }

    public void shutdown() {
        stop();
        executor.shutdown();
        messageConsumer.close();
    }

    private void consumeAndProcessNextMessage() {
        Optional<T> message = messageConsumer.poll();
        message.ifPresent(messageProcessor::process);
        messageConsumer.commit();
    }
}