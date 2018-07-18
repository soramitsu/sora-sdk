package jp.co.soramitsu.sora.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.stream.IntStream;
import lombok.val;


public class Flattener {

  private int features = 0;
  private char dictkey = '/';
  private char arrkey = '_';
  private ObjectMapper mapper = new ObjectMapper();

  private void flatten(JsonNode root, String path, ObjectNode out) {
    if (root.isObject()) {
      if (root.size() == 0) {
        // it is empty object
        out.set(path, root);
        return;
      }

      val fields = root.fields();
      fields.forEachRemaining(
          field -> flatten(field.getValue(), path + dictkey + sanitize(field.getKey()), out));
    } else if (root.isArray()) {
      if (root.size() == 0) {
        // it is empty array
        out.set(path, root);
        return;
      }

      IntStream
          .range(0, root.size())
          .forEachOrdered(i -> flatten(root.get(i), path + arrkey + i, out));
    } else if (root.isValueNode()) {
      out.set(path, root);

    } else {
      throw new FlattenerException("type is neigher object, array or value");
    }
  }

  private void deflatten(JsonNode root, ObjectNode out) {
    throw new RuntimeException("not implemented");
  }

  public Flattener() {
  }

  public Flattener(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public Flattener setObjectMapper(ObjectMapper mapper) {
    this.mapper = mapper;
    return this;
  }

  public Flattener setDictionaryKey(char key) {
    this.dictkey = key;
    return this;
  }

  public Flattener setArrayKey(char key) {
    this.arrkey = key;
    return this;
  }


  public JsonNode flatten(JsonNode root) {
    if (!root.isObject()) {
      return root;
    }

    ObjectNode node = mapper.createObjectNode();
    flatten(root, "", node);

    return node;
  }

  public JsonNode deflatten(JsonNode root) {
    if (!isFlattened(root)) {
      throw new FlattenerException("input json is not flattened");
    }

    ObjectNode out = mapper.createObjectNode();
    deflatten(root, out);

    return out;
  }

  public boolean isFlattened(JsonNode root) {
    if (!root.isObject()) {
      return false;
    }

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

      if (!key.startsWith(Character.toString(dictkey))) {
        // does not start with dictkey
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
   * @throws FlattenerException when length of the first part is not equal to length of second part
   */
  protected String desanitize(String k) {
    String[] a = k.split(":", 2);
    assert a.length == 2;

    int len = Integer.parseInt(a[0]);
    String key = a[1];
    if (len != key.length()) {
      throw new FlattenerException("wrongly escaped key: " + k);
    }

    return key;
  }
}
