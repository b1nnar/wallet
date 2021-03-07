package ro.alexandru.wallet.s2;

import ro.alexandru.wallet.s2.messaging.KafkaMessageConsumer;
import ro.alexandru.wallet.s2.messaging.KafkaMessageConsumerConfig;
import ro.alexandru.wallet.s2.messaging.MessageConsumer;
import ro.alexandru.wallet.s2.messaging.MessageConsumerProcess;
import ro.alexandru.wallet.s2.messaging.MessageProcessor;

import static ro.alexandru.wallet.s2.messaging.KafkaMessageConsumer.STRING_DESERIALIZER;

public class MessagingApp {

    public static void main(String[] args) throws InterruptedException {
        KafkaMessageConsumerConfig kafkaMessageConsumerConfig = new KafkaMessageConsumerConfig(
                "localhost:9092", "consumer_group_1", "TEST.01"
        );

        MessageConsumer<String> messageConsumer = new KafkaMessageConsumer<>(kafkaMessageConsumerConfig, STRING_DESERIALIZER);
        MessageProcessor<String> messageProcessor = value -> System.out.println("Processed value :" + value);
        MessageConsumerProcess<String> messageConsumerProcess = new MessageConsumerProcess<>(messageConsumer, messageProcessor);

        System.out.println("Starting consumer process");
        messageConsumerProcess.start();

        Thread.sleep(10000);

        messageConsumerProcess.stop();
        messageConsumerProcess.shutdown();
    }
}