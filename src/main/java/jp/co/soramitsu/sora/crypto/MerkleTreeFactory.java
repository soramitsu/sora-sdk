package jp.co.soramitsu.sora.crypto;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import org.spongycastle.util.Arrays;

public class MerkleTreeFactory {

  private MessageDigest digest;

  /**
   * Creates instance of MerkleTree with given digest.
   *
   * @param digest implementation of the digest algorithm
   * @apiNote after you created the instance, method {@link #createFromLeafs(List)}
   * should be called to calculate tree hashes
   */
  public MerkleTreeFactory(@NonNull MessageDigest digest) {
    this.digest = digest;
  }

  /**
   * Calculates hash by concatenating two hashes <code>a</code> and <code>b</code>
   *
   * @param a left hash
   * @param b right hash
   * @return digest(a | | b) where || is a concatenation
   */
  public byte[] hash(byte[] a, byte[] b) {
    return digest.digest(Arrays.concatenate(a, b));
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
   * Allocates new hash tree represented as byte[][]
   *
   * @param leafs number of leafs in this tree
   * @return allocated tree filled with nulls
   */
  public static byte[][] allocateEmptyTree(int leafs) {
    int size = ceilToPowerOf2(leafs * 2) - 1;
    return new byte[size][];
  }


  /**
   * Calculates merkle tree given leafs.
   *
   * @param leafs the bottom-most level of the tree (e.g. leafs)
   */
  public MerkleTree createFromLeafs(final List<byte[]> leafs) {
    if (leafs.isEmpty()) {
      throw new IllegalArgumentException("tree can be calculated from at least one item");
    }

    byte[][] tree = allocateEmptyTree(leafs.size());

    List<byte[]> nextLevel = new ArrayList<>(tree.length);

    while (!leafs.isEmpty()) {
      int leftmostLevelNode = ceilToPowerOf2(leafs.size()) - 1;
      System.arraycopy(leafs.toArray(), 0, tree, leftmostLevelNode, leafs.size());

      // calculate next level
      while (leafs.size() > 1) {
        // we can get 2 elements
        nextLevel.add(
            hash(leafs.remove(0), leafs.remove(0))
        );
      }

      if (leafs.size() == 1) {
        // orphan element; we do not calculate hash from it, just pass it to the next level
        nextLevel.add(
            leafs.remove(0)
        );
      }

      if (nextLevel.size() == 1) {
        // this is root
        tree[0] = nextLevel.remove(0);
        break;
      }

      // level is clear at this point
      assert leafs.isEmpty();
      leafs.addAll(nextLevel);
      nextLevel.clear();
    }

    return new MerkleTree(tree);
  }
}
