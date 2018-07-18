package jp.co.soramitsu.sora.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import jp.co.soramitsu.jackson.OneCodeMapper;
import lombok.NonNull;
import lombok.Setter;

public class Hashifier {

  @Setter
  private MessageDigest digest;

  @Setter
  private ObjectMapper mapper = new ObjectMapper();

  private ObjectMapper onecoder = new OneCodeMapper();

  public Hashifier(@NonNull MessageDigest digest) {
    this.digest = digest;
  }

  /**
   * Hashes top-level key-value pairs from input JSON. Number of output hashes
   * equals to number of top-level keys in input JSON.
   *
   * @param root JSON Object
   * @return list of hashes. Hash is specified by {@link #digest}
   * @throws HashifyException when input json is not an object
   */
  public List<byte[]> hashify(JsonNode root) {
    if (!root.isObject()) {
      throw new HashifyException("input json is not an object");
    }

    List<byte[]> c = new ArrayList<>(root.size());

    root.fields()
        .forEachRemaining(field -> {
          // create new object, one per key-value
          ObjectNode out = mapper.createObjectNode();
          out.set(field.getKey(), field.getValue());

          try {
            byte[] serialized = onecoder.writeValueAsBytes(out);

            c.add(digest.digest(serialized));
          } catch (JsonProcessingException e) {
            throw new HashifyException(e);
          }
        });

    return c;
  }
}
