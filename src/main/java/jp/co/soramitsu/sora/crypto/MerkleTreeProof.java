package jp.co.soramitsu.sora.crypto;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import lombok.NonNull;
import lombok.Value;

@Value
public class MerkleTreeProof {

  // [element] [path...] [root]
  List<MerkleNode> path;

  /**
   * <pre>
   * {@code
   * MessageDigest instance = MessageDigest.getInstance({@link #hashAlgorithm});
   * }
   * </pre>
   */
  String hashAlgorithm;

  /**
   * Verifies {@link MerkleTreeProof} path against supplied <code>root</code>
   */
  public boolean verify(@NonNull Hash root) throws NoSuchAlgorithmException {
//    MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
//
//    // 2^path.size()-1
//    ArrayTree hashTree = ArrayTree.createWithNLeafs((1 << path.size()) - 1);
//
//    path.forEach(n -> hashTree.set(n.getPosition(), n.getHash()));
//    // TODO
    return false;
  }



}
