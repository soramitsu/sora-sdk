package jp.co.soramitsu.sora.crypto;

import static jp.co.soramitsu.sora.common.Util.ceilToPowerOf2;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jp.co.soramitsu.sora.common.ArrayTree;
import jp.co.soramitsu.sora.common.ArrayTreeFactory;
import jp.co.soramitsu.sora.common.InvalidLeafNumberException;
import lombok.NonNull;
import lombok.val;

public class MerkleTreeFactory {

  private MessageDigest digest;

  /**
   * Creates instance of MerkleTree with given digest.
   *
   * @param digest implementation of the digest algorithm
   * @apiNote after you created the instance, method {@link #createFromLeafs(List)} should be called
   * to calculate tree hashes
   */
  public MerkleTreeFactory(@NonNull MessageDigest digest) {
    this.digest = digest;
  }

  /**
   * Calculates hash by concatenating two hashes <code>a</code> and <code>b</code>
   *
   * @param a left hash
   * @param b right hash
   * @return digest(a + b) where + is a concatenation
   */
  public Hash hash(@NonNull Hash a, @NonNull Hash b) {
    byte[] bytes = digest.digest(
        org.spongycastle.util.Arrays.concatenate(a.getData(), b.getData())
    );

    return new Hash(bytes);
  }

  /**
   * Calculates new {@link MerkleTree} given leafs.
   *
   * @param leafs the bottom-most level of the tree (e.g. leafs)
   * @return valid {@link MerkleTree}
   * @throws IllegalArgumentException when number of leafs is not at least 1
   */
  public MerkleTree createFromLeafs(@NonNull final List<Hash> leafs)
      throws InvalidLeafNumberException {
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

        if (Objects.nonNull(left) && Objects.nonNull(right)) {
          nextLevel.add(
              hash(left, right)
          );
        } else if (Objects.nonNull(left)) {
          nextLevel.add(left);
        } else if (Objects.nonNull(right)) {
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

  // TODO

  /**
   * Creates new Merkle Tree from tree bytes. Performs validity check. This method should be used
   * always when you read the tree from the untrusted source.
   *
   * @param tree merkle tree as described in {@link MerkleTree}
   * @return valid {@link MerkleTree}
   * @throws InvalidMerkleTreeException when <code>tree</code> is not valid (roots are different).
   */
  public MerkleTree createFromFullTree(@NonNull final ArrayTree<Hash> tree)
      throws InvalidMerkleTreeException {
    MerkleTree mt = new MerkleTree(digest, tree);
    MerkleTree check = createFromLeafs(tree.getLeafs());

    if(!mt.root().equals(check.root())){
      throw new InvalidMerkleTreeException(mt.root(), check.root());
    }

    return mt;
  }
}
