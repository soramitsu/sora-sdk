package jp.co.soramitsu.sora.sdk.crypto.json.flattener;

import static java.lang.String.valueOf;
import static jp.co.soramitsu.sora.sdk.crypto.common.Util.ensureArraySize;
import static jp.co.soramitsu.sora.sdk.crypto.json.flattener.KeyTypeEnum.ARRAY;
import static jp.co.soramitsu.sora.sdk.crypto.json.flattener.KeyTypeEnum.DICT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.IntStream;
import jp.co.soramitsu.sora.sdk.crypto.json.BadJsonException;
import lombok.NoArgsConstructor;
import lombok.val;
import net.jcip.annotations.ThreadSafe;

@NoArgsConstructor
@ThreadSafe
public class Flattener {

  public static final String DICT_DELIMITER = "/";
  public static final String ARRAY_DELIMITER = "_";

  private ObjectMapper mapper = new ObjectMapper();

  public Flattener(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  private void flatten(JsonNode root, String path, ObjectNode out) {
    if (root.isObject()) {
      if (root.size() == 0) {
        // it is an empty object
        out.set(path, root);
        return;
      }

      root.fields()
          .forEachRemaining(
              field -> flatten(field.getValue(), path + DICT_DELIMITER + sanitize(field.getKey()),
                  out)
          );
    } else if (root.isArray()) {
      if (root.size() == 0) {
        // it is an empty array
        out.set(path, root);
        return;
      }

      IntStream
          .range(0, root.size())
          .forEachOrdered(i -> flatten(root.get(i), path + ARRAY_DELIMITER + i, out));
    } else if (root.isValueNode()) {
      out.set(path, root);

    } else {
      throw new BadJsonException(root);
    }
  }

  public ObjectNode flatten(ObjectNode root) {
    ObjectNode node = mapper.createObjectNode();
    flatten(root, "", node);

    return node;
  }

  /**
   * Makes flat JSON original.
   *
   * @param root input JSON Object
   * @return original JSON
   * @throws DeflattenException when flattened json has incorrect format
   */
  public ObjectNode deflatten(ObjectNode root) {
    ObjectNode deflattened = mapper.createObjectNode();
    root.fields().forEachRemaining(field -> processField(deflattened, field));
    return deflattened;
  }

  private void processField(final JsonNode out, Entry<String, JsonNode> field) {
    val parser = new FlattenedKeyParser(field.getKey());
    List<Token> tokens;
    try {
      tokens = parser.parse();
    } catch (ParsingException e) {
      throw new DeflattenException(e); // runtime exception
    }

    if (tokens.get(0).getType() == ARRAY) {
      throw new DeflattenException("array top level key is not allowed: " + field.getKey());
    }

    final int totalTokens = tokens.size();
    JsonNode current = out;
    for (int i = 0; i < totalTokens - 1; i++) {
      val token = tokens.get(i);
      val nextToken = tokens.get(i + 1);
      current = findOrCreateNode(current, token, nextToken);
    }

    val lastToken = tokens.get(totalTokens - 1);
    lastToken.setValue(current, field.getValue());
  }

  private JsonNode findOrCreateNode(JsonNode root, Token token, Token nextToken) {
    if (root.isObject()) {
      assert token.getType() == DICT;
      String key = (String) token.getValue();

      ObjectNode n = (ObjectNode) root;
      if (!n.has(key)) {
        JsonNode node = nextToken.createNode(mapper);
        n.set(key, node);
        return node;
      }

      return n.get(key);

    } else if (root.isArray()) {
      assert token.getType() == ARRAY;
      Integer index = (Integer) token.getValue();

      ArrayNode n = (ArrayNode) root;
      if (index > n.size() || !n.hasNonNull(index)) {
        ensureArraySize(n, index);
        JsonNode node = nextToken.createNode(mapper);
        n.set(index, node);
        return node;
      }

      return n.get(index);

    } else {
      throw new DeflattenException("node is not array or object type");
    }
  }


  public boolean isFlattened(ObjectNode root) {
    val fields = root.fields();
    while (fields.hasNext()) {
      val field = fields.next();
      val key = field.getKey();
      val value = field.getValue();

      if (!value.isValueNode() &&  // not value node
          // and not empty object or empty array
          (value.size() != 0 && (value.isArray() || value.isObject()))
      ) {
        return false;
      }

      if (!key.startsWith(DICT_DELIMITER)) {
        // does not start with DICT_DELIMITER
        return false;
      }
    }

    return true;
  }

  /**
   * Encodes string as {length base 10}:{string}
   *
   * @param k input string
   * @return encoded string
   */
  private String sanitize(String k) {
    return valueOf(k.length()) + ":" + k;
  }
}
