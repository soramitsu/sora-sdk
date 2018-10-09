package jp.co.soramitsu.sora.sdk.crypto.json;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import jp.co.soramitsu.sora.sdk.json.JsonUtil;
import org.junit.Test;

public class HashifierTestJ {

  private final Hashifier hashifier = new Hashifier();
  private final ObjectMapper mapper = JsonUtil.buildMapper();


  @Test
  public void rearrangeTest() throws IOException {

    assertEquals(
        hashifier.hashify(getDefaultRootNode()),
        hashifier.hashify(getSortedRootNode()));
  }

  private ObjectNode getDefaultRootNode() {
    return fillNode(
        new SimpleEntry<>("str4", "str4"),
        new SimpleEntry<>("str3", "str3"),
        new SimpleEntry<>("str1", "str1"),
        new SimpleEntry<>("str2", "str2")
    );
  }

  private ObjectNode getSortedRootNode() {
    return fillNode(
        new SimpleEntry<>("str1", "str1"),
        new SimpleEntry<>("str2", "str2"),
        new SimpleEntry<>("str3", "str3"),
        new SimpleEntry<>("str4", "str4")
    );
  }

  @SafeVarargs
  private final ObjectNode fillNode(Map.Entry<String, String>... values) {
    final ObjectNode rootNode = mapper.createObjectNode();
    Arrays.stream(values).forEach(v -> rootNode.set(v.getKey(), new TextNode(v.getValue())));
    return rootNode;
  }
}
