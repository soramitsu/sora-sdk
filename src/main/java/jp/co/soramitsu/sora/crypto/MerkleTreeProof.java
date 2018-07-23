package jp.co.soramitsu.sora.crypto;

import static jp.co.soramitsu.sora.common.ArrayTree.getParent;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import jp.co.soramitsu.sora.common.Util;
import lombok.NonNull;
import lombok.Value;

@Value
public class MerkleTreeProof {

  // [element] [path...]
  List<MerkleNode> path;

  /**
   * Verifies {@link MerkleTreeProof} path against supplied <code>root</code>
   */
  public boolean verify(@NonNull MessageDigest digest, @NonNull Hash root) {
    LinkedList<MerkleNode> copy = new LinkedList<>(path);

    while (copy.size() > 1) {
      LinkedList<MerkleNode> pair = new LinkedList<>();
      // pop 2 elements
      pair.addLast(copy.removeFirst());
      pair.addLast(copy.removeFirst());

      // sort them based on position
      pair.sort(Comparator.comparing(MerkleNode::getPosition));

      // calculate hash and push it to the beginning
      Hash hash = Util.hash(
          digest,
          pair.getFirst().getHash(),
          pair.getLast().getHash()
      );
      int parentPos = getParent(pair.getFirst().getPosition());
      copy.addFirst(new MerkleNode(parentPos, hash));
    }

    MerkleNode calculatedRoot = copy.getFirst();

    return calculatedRoot.getPosition() == 0 && calculatedRoot.getHash().equals(root);
  }


}
