package ro.alexandru.wallet.messaging.serializer;

import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONSerializer<T> implements Serializer<T> {

    private static final Logger LOG = LoggerFactory.getLogger(JSONSerializer.class);

    @Override
    public byte[] serialize(String topic, T value) {
        if (value == null) {
            return null;
        }
        try {
            return JSONObjectMapper.INSTANCE.writeValueAsBytes(value);
        } catch (Exception e) {
            LOG.error("Error serializing into JSON: `{}`. Exception: `{}`", value, Utils.stackTrace(e));
            throw new SerializationException(String.format("Error serializing into JSON: `%s`", value), e);
        }
    }
}
