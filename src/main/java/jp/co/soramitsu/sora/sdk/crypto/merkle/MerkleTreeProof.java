package jp.co.soramitsu.sora.sdk.crypto.merkle;

import static jp.co.soramitsu.sora.sdk.crypto.common.ArrayTree.getParentIndex;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import jp.co.soramitsu.sora.sdk.crypto.common.Hash;
import jp.co.soramitsu.sora.sdk.crypto.common.Util;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.jcip.annotations.NotThreadSafe;

@ToString
@RequiredArgsConstructor
@Getter
@NotThreadSafe
public class MerkleTreeProof {

  private final MessageDigest digest;

  // [element] [path...]
  private final List<MerkleNode> path;

  /**
   * Verifies {@link MerkleTreeProof} path against supplied <code>root</code>
   */
  public boolean verify(@NonNull Hash root) {
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
      int parentPos = getParentIndex(pair.getFirst().getPosition());
      copy.addFirst(new MerkleNode(parentPos, hash));
    }

    MerkleNode calculatedRoot = copy.getFirst();

    return calculatedRoot.getPosition() == 0 && calculatedRoot.getHash().equals(root);
  }


}
