package ro.alexandru.wallet.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MessageConsumerProcess<T> {

    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumerProcess.class);

    private final String processName;
    private final MessageConsumer<T> messageConsumer;
    private final MessageProcessor<T> messageProcessor;
    private final ScheduledExecutorService executor;
    private ScheduledFuture<?> scheduledFuture = null;

    public MessageConsumerProcess(String processName, MessageConsumer<T> messageConsumer, MessageProcessor<T> messageProcessor) {
        this.processName = processName;
        this.messageConsumer = messageConsumer;
        this.messageProcessor = messageProcessor;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        LOG.info("Starting message consumer process `{}`", processName);

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