package jp.co.soramitsu.sora.crypto.merkle;

import static java.util.Objects.nonNull;
import static jp.co.soramitsu.sora.crypto.common.Util.ceilToPowerOf2;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import jp.co.soramitsu.sora.crypto.common.ArrayTree;
import jp.co.soramitsu.sora.crypto.common.ArrayTreeFactory;
import jp.co.soramitsu.sora.crypto.common.Hash;
import jp.co.soramitsu.sora.crypto.common.InvalidNodeNumberException;
import jp.co.soramitsu.sora.crypto.common.Util;
import lombok.NonNull;
import lombok.val;

public class MerkleTreeFactory {

  private final MessageDigest digest;

  /**
   * Creates instance of MerkleTree with given digest.
   *
   * @param digest implementation of the digest algorithm
   * @apiNote after you created the instance, method {@link #createFromLeaves(List)} should be
   * called to calculate tree hashes
   */
  public MerkleTreeFactory(@NonNull MessageDigest digest) {
    this.digest = digest;
  }

  /**
   * Calculates new {@link MerkleTree} given leaves.
   *
   * @param leafs the bottom-most level of the tree (e.g. leaves)
   * @return valid {@link MerkleTree}
   * @throws IllegalArgumentException when number of leaves is not at least 1
   */
  public MerkleTree createFromLeaves(@NonNull final List<Hash> leafs)
      throws InvalidNodeNumberException {
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

    return new MerkleTree(tree);
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
    MerkleTree mt = new MerkleTree(tree);
    MerkleTree check = createFromLeaves(tree.getLeaves());

    if (!mt.root().equals(check.root())) {
      throw new RootHashMismatchException(mt.root(), check.root());
    }

    return mt;
  }
}
