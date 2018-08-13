package jp.co.soramitsu.sora.crypto.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.stream.IntStream;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;


@NoArgsConstructor
public class Flattener {

  @Setter
  private char dictkey = '/';
  @Setter
  private char arrkey = '_';
  @Setter
  private ObjectMapper mapper = new ObjectMapper();

  private void flatten(JsonNode root, String path, ObjectNode out) {
    if (root.isObject()) {
      if (root.size() == 0) {
        // it is empty object
        out.set(path, root);
        return;
      }

      root.fields()
          .forEachRemaining(
              field -> flatten(field.getValue(), path + dictkey + sanitize(field.getKey()), out)
          );
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
      throw new FlattenException("type is neigher object, array or value");
    }
  }

  private void deflatten(JsonNode root, ObjectNode out) {
    // TODO: implement BSM-91
    throw new RuntimeException("not implemented");
  }

  public Flattener(ObjectMapper mapper) {
    this.mapper = mapper;
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
      throw new FlattenException("input json is not flattened");
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
