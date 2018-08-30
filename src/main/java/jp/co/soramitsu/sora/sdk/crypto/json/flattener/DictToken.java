package jp.co.soramitsu.sora.sdk.crypto.json.flattener;

import static jp.co.soramitsu.sora.sdk.crypto.json.flattener.KeyTypeEnum.DICT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Value;

@Value
class DictToken implements Token {

  private String key;

  @Override
  public String getValue() {
    return key;
  }

  @Override
  public JsonNode createNode(ObjectMapper mapper) {
    return mapper.createObjectNode();
  }

  @Override
  public KeyTypeEnum getType() {
    return DICT;
  }

  @Override
  public void setValue(JsonNode root, JsonNode value) {
    ObjectNode obj = (ObjectNode) root;
    obj.set(key, value);
  }
}
