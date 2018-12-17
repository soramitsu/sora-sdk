package jp.co.soramitsu.sora.sdk.did.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.ParseException;
import jp.co.soramitsu.sora.sdk.did.model.dto.DateTimeParseException;
import jp.co.soramitsu.sora.sdk.did.validation.ISO8601DateTimeFormatter;

public class TimeCombinedSerializer {

  public static class TimeISO8601Serializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      try {
        ISO8601DateTimeFormatter.parse(value);
        gen.writeString(value);
      } catch (ParseException e) {
        throw new DateTimeParseException(value, e);
      }
    }
  }

  public static class TimeISO8601Deserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      String value = p.getText();
      try {
        ISO8601DateTimeFormatter.parse(value);
        return value;
      } catch (ParseException e) {
        throw new DateTimeParseException(value, e);
      }
    }
  }
}
