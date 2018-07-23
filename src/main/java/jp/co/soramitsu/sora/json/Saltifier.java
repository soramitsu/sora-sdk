package jp.co.soramitsu.sora.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Saltifier {

  private final ObjectMapper mapper;
  private final SaltGenerator generator;
  private final String valueKey;
  private final String saltKey;

  public Saltifier(ObjectMapper mapper, SaltGenerator generator) {
    this(mapper, generator, "v", "s");
  }

  public Saltifier(ObjectMapper mapper, SaltGenerator generator, String valueKey, String saltKey) {
    this.mapper = mapper;
    this.generator = generator;
    this.valueKey = valueKey;
    this.saltKey = saltKey;
  }

  public JsonNode saltify(JsonNode root) {
    if (root.isValueNode()) {
      return addSalt(root);

    } else if (root.isArray()) {
      if (root.size() == 0) {
        return addSalt(mapper.createArrayNode());
      }

      ArrayNode arr = mapper.createArrayNode();
      root.forEach(el -> arr.add(saltify(el)));

      return arr;

    } else if (root.isObject()) {
      if (root.size() == 0) {
        return addSalt(mapper.createObjectNode());
      }

      ObjectNode obj = mapper.createObjectNode();
      root.fields().forEachRemaining(entry -> obj.set(entry.getKey(), saltify(entry.getValue())));

      return obj;
    }

    return null;
  }

  protected JsonNode addSalt(JsonNode node) {
    ObjectNode obj = mapper.createObjectNode();

    obj.set(valueKey, node);
    obj.put(saltKey, generator.next());

    return obj;
  }
}
