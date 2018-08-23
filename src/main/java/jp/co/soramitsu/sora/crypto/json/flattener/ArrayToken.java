package jp.co.soramitsu.sora.crypto.json.flattener;

import static jp.co.soramitsu.sora.crypto.common.Util.ensureArraySize;
import static jp.co.soramitsu.sora.crypto.json.flattener.KeyTypeEnum.ARRAY;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Value;

@Value
class ArrayToken implements Token {

  private Integer index;

  @Override
  public Integer getValue() {
    return index;
  }

  @Override
  public JsonNode createNode(ObjectMapper mapper) {
    return mapper.createArrayNode();
  }

  @Override
  public KeyTypeEnum getType() {
    return ARRAY;
  }

  @Override
  public void setValue(JsonNode root, JsonNode value) {
    ArrayNode obj = (ArrayNode) root;
    ensureArraySize(obj, index);
    obj.set(index, value);
  }
}
