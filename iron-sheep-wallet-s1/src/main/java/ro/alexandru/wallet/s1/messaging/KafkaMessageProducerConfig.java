package ro.alexandru.wallet.s1.messaging;

public class KafkaMessageProducerConfig {

    private final String brokerAddress;
    private final String clientId;
    private final String topic;

    public KafkaMessageProducerConfig(String brokerAddress, String clientId, String topic) {
        this.brokerAddress = brokerAddress;
        this.clientId = clientId;
        this.topic = topic;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public String getClientId() {
        return clientId;
    }

    public String getTopic() {
        return topic;
    }
}
