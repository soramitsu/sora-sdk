package jp.co.soramitsu.sora.sdk.crypto.common;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.SignatureException;
import jp.co.soramitsu.sora.sdk.crypto.json.JSONCanonizer;
import jp.co.soramitsu.sora.sdk.model.dto.Options;
import org.spongycastle.util.Arrays;

public class Util {

  private Util() {
  }

  /**
   * Ceil to the next power of 2
   *
   * @return <code>items</code> = 2, returns 2. <code>items</code> = 3, returns 4.
   */
  public static int ceilToPowerOf2(int items) {
    int highest = Integer.highestOneBit(items);
    return items == highest ? items : highest * 2;
  }

  /**
   * Calculates hash by concatenating two hashes <code>a</code> and <code>b</code>
   *
   * @param a left hash
   * @param b right hash
   * @return digest(a + b) where + is a concatenation
   */
  public static Hash hash(MessageDigest d, Hash a, Hash b) {
    return new Hash(
        d.digest(
            Arrays.concatenate(
                a.getData(),
                b.getData()
            )
        )
    );
  }

  public static byte[] serializeWithOptions(
      JSONCanonizer canonizer, ObjectNode root, Options options)
      throws IOException {
    byte[] canonized = canonizer.canonize(root);
    byte[] opts = canonizer.canonize(options);
    return Arrays.concatenate(canonized, opts);
  }

  public static ObjectNode deepCopyWithoutProofNode(ObjectNode root) {
    ObjectNode out = root.deepCopy();
    out.remove(Consts.PROOF_KEY);
    return out;
  }

  public static void verifyAlgorithmType(Options options, Signature signature)
      throws SignatureException {
    String type = options.getType().getAlgorithm();
    if (!type.equals(signature.getAlgorithm())) {
      throw new SignatureException(
          String
              .format("options (%s) and signature (%s) have different signature algorithms", type,
                  signature.getAlgorithm())
      );
    }
  }

  /**
   * Appends null nodes to an array node until it has given size.
   */
  public static void ensureArraySize(ArrayNode node, Integer size) {
    while (node.size() <= size) {
      node.add(NullNode.getInstance());
    }
  }
}
