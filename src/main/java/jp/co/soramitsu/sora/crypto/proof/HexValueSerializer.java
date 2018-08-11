package jp.co.soramitsu.sora.crypto.proof;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import javax.xml.bind.DatatypeConverter;

public class HexValueSerializer extends JsonSerializer<byte[]> {

  @Override
  public void serialize(byte[] value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(DatatypeConverter.printHexBinary(value));
  }
}
