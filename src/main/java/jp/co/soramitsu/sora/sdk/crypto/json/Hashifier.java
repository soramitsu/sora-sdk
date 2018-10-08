package jp.co.soramitsu.sora.sdk.crypto.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import jp.co.soramitsu.sora.sdk.crypto.common.Hash;
import jp.co.soramitsu.sora.sdk.crypto.common.SecurityProvider;
import jp.co.soramitsu.sora.sdk.did.model.type.DigestTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.jcip.annotations.NotThreadSafe;
import org.spongycastle.util.Arrays;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
@NotThreadSafe
public class Hashifier {

  private final MessageDigest digest;
  private final JSONCanonizer canonizer;

  public Hashifier() {
    this(new SecurityProvider().getMessageDigest(DigestTypeEnum.SHA3_256));
  }

  public Hashifier(MessageDigest digest) {
    this.digest = digest;
    this.canonizer = new JSONCanonizerWithOneCoding();
  }

  public Hash hashJsonField(Entry<String, JsonNode> field) throws IOException {
    // json: {key}canonize({value})
    byte[] serialized = Arrays.concatenate(
        field.getKey().getBytes(),
        canonizer.canonize(field.getValue())
    );

    byte[] hash = digest.digest(serialized);

    return new Hash(hash);

  }

  public void hashify(ObjectNode root, Consumer<Hash> consumer) throws IOException {
    hashify(root, (k, h) -> consumer.accept(h));
  }

  public void hashify(ObjectNode root, BiConsumer<String, Hash> consumer) throws IOException {
    for (Iterator<Entry<String, JsonNode>> it = root.fields(); it.hasNext(); ) {
      val field = it.next();
      consumer.accept(field.getKey(), hashJsonField(field));
    }
  }

  /**
   * Hashes top-level key-value pairs from input JSON. Number of output hashes equals to number of
   * top-level keys in input JSON.
   *
   * @param root JSON Object
   * @return list of hashes. Hash is specified by {@link #digest}
   * @throws NotJsonObjectException when input json is not an object
   */
  public List<Hash> hashify(ObjectNode root) throws IOException {
    List<Hash> hashes = new ArrayList<>(root.size());
    hashify(root, (Consumer<Hash>) hashes::add);
    return hashes;
  }

  /**
   * The opposite operation to hashify.
   *
   * @param hashes a set of hashes required to dehashify
   * @param root preimage JSON of the hashes
   * @return a Map, where hash maps to the key in JSON
   * @throws IOException when JSON can not be processed
   */
  public Map<Hash, String> dehashify(Set<Hash> hashes, ObjectNode root) throws IOException {
    Map<Hash, String> map = new HashMap<>(hashes.size());

    hashify(root, (key, h) -> {
      if (hashes.contains(h)) {
        map.put(h, key);
      }
    });

    return map;
  }
}
