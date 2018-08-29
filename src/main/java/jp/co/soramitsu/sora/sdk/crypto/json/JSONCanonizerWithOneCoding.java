package jp.co.soramitsu.sora.sdk.crypto.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jp.co.soramitsu.jackson.OneCodeMapper;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class JSONCanonizerWithOneCoding implements JSONCanonizer {

  private final ObjectMapper mapper = new OneCodeMapper();

  @Override
  public byte[] canonize(Object obj) throws IOException {
    return mapper.writeValueAsBytes(obj);
  }
}
