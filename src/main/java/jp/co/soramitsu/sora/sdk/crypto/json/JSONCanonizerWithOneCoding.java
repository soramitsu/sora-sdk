package jp.co.soramitsu.sora.sdk.crypto.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jp.co.soramitsu.jackson.OneCoder;
import jp.co.soramitsu.sora.sdk.json.JsonUtil;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class JSONCanonizerWithOneCoding implements JSONCanonizer {

  private final ObjectMapper mapper = JsonUtil.buildMapper();

  @Override
  public byte[] canonize(Object obj) throws IOException {
    byte[] bytes = mapper.writeValueAsBytes(obj);
    JsonNode node = mapper.readTree(bytes);
    OneCoder c = new OneCoder();
    c.writeJsonNode(node);
    String str = c.toString();
    return str.getBytes(c.getCharset());
  }
}
