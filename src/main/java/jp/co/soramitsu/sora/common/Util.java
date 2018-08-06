package jp.co.soramitsu.sora.common;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.MessageDigest;
import jp.co.soramitsu.sora.crypto.Hash;
import jp.co.soramitsu.sora.crypto.common.Consts;
import jp.co.soramitsu.sora.crypto.proof.Options;
import jp.co.soramitsu.sora.crypto.service.JSONCanonizer;
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

  static public byte[] serializeWithOptions(
      JSONCanonizer canonizer, ObjectNode root, Options options)
      throws IOException {
    byte[] canonized = canonizer.canonize(root);
    byte[] opts = canonizer.canonize(options);
    return Arrays.concatenate(canonized, opts);
  }

  static public ObjectNode deepCopyWithoutProofNode(ObjectNode root) {
    ObjectNode out = root.deepCopy();
    out.remove(Consts.PROOF_KEY);
    return out;
  }
}
