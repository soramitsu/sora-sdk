package jp.co.soramitsu.sora.crypto.proof;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import javax.xml.bind.DatatypeConverter;

public class HexValueDeserializer extends JsonDeserializer<byte[]> {

  /**
   * @throws IllegalArgumentException when input data is not hex
   */
  @Override
  public byte[] deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    return DatatypeConverter.parseHexBinary(p.getText());
  }
}
