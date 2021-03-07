package ro.alexandru.wallet.s2.messaging;

public class KafkaMessageConsumerConfig {

    private final String brokerAddress;
    private final String consumerGroupId;
    private final String topic;

    public KafkaMessageConsumerConfig(String brokerAddress, String consumerGroupId, String topic) {
        this.brokerAddress = brokerAddress;
        this.consumerGroupId = consumerGroupId;
        this.topic = topic;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public String getConsumerGroupId() {
        return consumerGroupId;
    }

    public String getTopic() {
        return topic;
    }
}
