package jp.co.soramitsu.sora.sdk.crypto.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jp.co.soramitsu.sora.sdk.crypto.common.SaltGenerator;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.jcip.annotations.ThreadSafe;

@AllArgsConstructor
@FieldDefaults(makeFinal = true)
@ThreadSafe
public class Saltifier {

  ObjectMapper mapper;
  SaltGenerator generator;
  String valueKey;
  String saltKey;

  public Saltifier(ObjectMapper mapper, SaltGenerator generator) {
    this(mapper, generator, "v", "s");
  }

  /**
   * Add salt to the every *value* field in the input JSON.
   *
   * <pre>
   *   {
   *     "a": 1,
   *     "b": [1, 2]
   *   }
   *
   *   =>
   *
   *   {
   *     "a": {"v": 1, "s": "<salt>"},
   *     "b": [
   *       {"v": 1, "s": "<salt>"},
   *       {"v": 2, "s": "<salt>"},
   *     ]
   *   }
   * </pre>
   *
   * @param root input JSON
   * @return new JSON, which has previous values plus random salt
   */
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
    } else {
      throw new BadJsonException(root);
    }
  }

  /**
   * The opposite operation to {@link #saltify(JsonNode)}
   *
   * @param root saltified JSON
   * @return initial JSON, before saltify operation
   */
  public JsonNode desaltify(JsonNode root) {
    if (root.isValueNode()) {
      return root;

    } else if (root.isArray()) {
      ArrayNode arr = mapper.createArrayNode();
      root.forEach(el -> arr.add(desaltify(el)));
      return arr;

    } else if (root.isObject()) {

      if (isValueSalted(root)) {
        return desaltify(root.get(valueKey));
      }

      ObjectNode obj = mapper.createObjectNode();
      root.fields().forEachRemaining(entry -> obj.set(entry.getKey(), desaltify(entry.getValue())));

      return obj;
    } else {
      throw new BadJsonException(root);
    }
  }

  private JsonNode addSalt(JsonNode node) {
    ObjectNode obj = mapper.createObjectNode();

    obj.set(valueKey, node);
    obj.put(saltKey, generator.next());

    return obj;
  }

  /**
   * Checks, if given json node is salted.
   */
  public boolean isSalted(JsonNode node) {
    if (node.isValueNode()) {
      return false;
    }

    if (node.isObject() && isValueSalted(node)) {
      return true;
    }

    if (node.isArray() || node.isObject()) {
      for (JsonNode n : node) {
        if (!isSalted(n)) {
          return false;
        }
      }

      return true;
    }

    throw new NotJsonObjectException(node);
  }

  private boolean isValueSalted(JsonNode node) {
    return node.hasNonNull(saltKey) && node.has(valueKey);
  }
}
