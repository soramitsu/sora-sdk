package jp.co.soramitsu.sora.sdk.crypto.merkle;

import static java.util.Objects.nonNull;
import static jp.co.soramitsu.sora.sdk.crypto.common.Util.ceilToPowerOf2;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import jp.co.soramitsu.sora.sdk.crypto.common.ArrayTree;
import jp.co.soramitsu.sora.sdk.crypto.common.ArrayTreeFactory;
import jp.co.soramitsu.sora.sdk.crypto.common.Hash;
import jp.co.soramitsu.sora.sdk.crypto.common.Util;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import net.jcip.annotations.NotThreadSafe;

@AllArgsConstructor
@NotThreadSafe
public class MerkleTreeFactory {

  private final MessageDigest digest;

  /**
   * Calculates new {@link MerkleTree} given leaves.
   *
   * @param leafs the bottom-most level of the tree (e.g. leaves)
   * @return valid {@link MerkleTree}
   * @throws IllegalArgumentException when number of leaves is not at least 1
   */
  public MerkleTree createFromLeaves(@NonNull final List<Hash> leafs) {
    ArrayTree<Hash> tree = ArrayTreeFactory.createWithNLeafs(leafs.size());  // throws

    List<Hash> nextLevel = new ArrayList<>(tree.size());

    while (!leafs.isEmpty()) {
      // copy current level (`leafs`) inside our tree at given positions
      int leftmostLevelNode = ceilToPowerOf2(leafs.size()) - 1;
      for (int i = 0; i < leafs.size(); i++) {
        tree.set(leftmostLevelNode + i, leafs.get(i));
      }

      // calculate next level
      while (leafs.size() > 1) {
        // we can get 2 elements
        val left = leafs.remove(0);
        val right = leafs.remove(0);

        if (nonNull(left) && nonNull(right)) {
          nextLevel.add(
              Util.hash(digest, left, right)
          );
        } else if (nonNull(left)) {
          nextLevel.add(left);
        } else if (nonNull(right)) {
          nextLevel.add(right);
        } else {
          // both null
          nextLevel.add(null);
        }
      }

      if (leafs.size() == 1) {
        // orphan element; we do not calculate hash from it, just pass it to the next level
        nextLevel.add(
            leafs.remove(0)
        );
      }

      if (nextLevel.size() == 1) {
        // this is root
        tree.set(0, nextLevel.remove(0));
        break;
      }

      // level is clear at this point
      assert leafs.isEmpty();
      leafs.addAll(nextLevel);
      nextLevel.clear();
    }

    return new MerkleTree(digest, tree);
  }

  /**
   * Creates new Merkle Tree from tree bytes. Performs validity check. This method should be used
   * always when you read the tree from the untrusted source.
   *
   * @param tree merkle tree as described in {@link MerkleTree}
   * @return valid {@link MerkleTree}
   * @throws RootHashMismatchException when <code>tree</code> is not valid (roots are different).
   */
  public MerkleTree createFromFullTree(@NonNull final ArrayTree<Hash> tree)
      throws RootHashMismatchException {
    MerkleTree mt = new MerkleTree(digest, tree);
    MerkleTree check = createFromLeaves(tree.getLeaves());

    if (!mt.getRoot().equals(check.getRoot())) {
      throw new RootHashMismatchException(mt.getRoot(), check.getRoot());
    }

    return mt;
  }
}
