package jp.co.soramitsu.sora.crypto.json.flattener;

import static jp.co.soramitsu.sora.crypto.json.flattener.KeyTypeEnum.ARRAY;
import static jp.co.soramitsu.sora.crypto.json.flattener.KeyTypeEnum.DICT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.IntStream;
import jp.co.soramitsu.sora.crypto.json.BadJsonException;
import lombok.NoArgsConstructor;
import lombok.val;


@NoArgsConstructor
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
      JsonNode node = null;

      if (nextToken.getType() == DICT) {
        node = mapper.createObjectNode();
      } else if (nextToken.getType() == ARRAY) {
        node = mapper.createArrayNode();
      }

      current = findOrCreateNode(current, token, node);
    }

    val lastToken = tokens.get(totalTokens - 1);
    setValue(current, lastToken, field.getValue());
  }

  private void setValue(JsonNode root, Token token, JsonNode value) {
    if (token.getType() == DICT) {
      assert root.isObject();
      String key = (String) token.getValue();
      ObjectNode obj = (ObjectNode) root;
      obj.set(key, value);

    } else if (token.getType() == ARRAY) {
      assert root.isArray();
      Integer index = (Integer) token.getValue();
      ArrayNode obj = (ArrayNode) root;
      ensureArraySize(obj, index);
      obj.set(index, value);

    }
  }

  private JsonNode findOrCreateNode(JsonNode root, Token token, JsonNode node) {
    if (root.isObject()) {
      String key = (String) token.getValue();

      ObjectNode n = (ObjectNode) root;
      if (!n.has(key)) {
        n.set(key, node);
        return node;
      }

      return n.get(key);

    } else if (root.isArray()) {
      Integer index = (Integer) token.getValue();

      ArrayNode n = (ArrayNode) root;
      if (index > n.size() || !n.hasNonNull(index)) {
        ensureArraySize(n, index);
        n.set(index, node);
        return node;
      }

      return n.get(index);

    } else {
      throw new DeflattenException("node is not array or object type");
    }
  }

  private void ensureArraySize(ArrayNode node, Integer size) {
    while (node.size() <= size) {
      node.add(NullNode.getInstance());
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
  protected String sanitize(String k) {
    return String.format("%s:%s",
        String.valueOf(k.length()),
        k
    );
  }

  /**
   * Decodes string from {length base 10}:{string}
   *
   * @param k encoded string
   * @return decoded string
   * @throws NumberFormatException when first part (before :) is not base 10 number
   * @throws FlattenException when length of the first part is not equal to length of second part
   */
  protected String desanitize(String k) {
    String[] a = k.split(":", 2);
    assert a.length == 2;

    int len = Integer.parseInt(a[0]);
    String key = a[1];
    if (len != key.length()) {
      throw new FlattenException("wrongly escaped key: " + k);
    }

    return key;
  }
}
