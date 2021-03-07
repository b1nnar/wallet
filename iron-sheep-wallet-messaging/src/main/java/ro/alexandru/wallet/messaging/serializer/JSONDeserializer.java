package ro.alexandru.wallet.messaging.serializer;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONDeserializer<T> implements Deserializer<T> {

    private static final Logger LOG = LoggerFactory.getLogger(JSONDeserializer.class);

    private final Class<T> typeClass;

    public JSONDeserializer(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return JSONObjectMapper.INSTANCE.readValue(bytes, typeClass);
        } catch (Exception e) {
            LOG.error("Error deserializing JSON: `{}`. Exception: `{}`", new String(bytes), Utils.stackTrace(e));
            throw new DeserializationException(String.format("Error deserializing JSON: `%s`", new String(bytes)), e);
        }
    }
}
