package jp.co.soramitsu.sora.crypto;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.val;
import org.spongycastle.util.Arrays;

public class MerkleTree {

  private MessageDigest digest;

  // [root] [intermediate nodes] [leafs]
  private byte[][] tree;

  /**
   * Creates new tree from given leafs with given digest function.
   * @param digest implementation of the digest algorithm
   * @param leafs list of leaf nodes
   */
  public MerkleTree(@NonNull MessageDigest digest, @NonNull List<byte[]> leafs) {
    this.digest = digest;

    create(leafs);
  }

  /**
   * Creates instance of MerkleTree with given digest.
   * @apiNote after you created the instance, method {@link #create(List)} should be called
   * to calculate tree hashes
   * @param digest implementation of the digest algorithm
   */
  public MerkleTree(@NonNull MessageDigest digest) {
    this.digest = digest;
  }

  /**
   * Calculates hash by concatenating two hashes <code>a</code> and <code>b</code>
   * @param a left hash
   * @param b right hash
   * @return digest(a || b) where || is a concatenation
   */
  public byte[] hash(byte[] a, byte[] b) {
    return digest.digest(Arrays.concatenate(a, b));
  }

  /**
   * Getter for the tree. Tree is stored "by levels", so first level is root and stored
   * as tree[0], next level (2 items) is stored in tree[1] and tree[2], and so on.
   * @return tree represented as [root] [intermediate nodes] [leafs]
   */
  public byte[][] getTree() {
    return tree;
  }

  /**
   * Ceil to the next power of 2
   * @param items
   * @return <code>items</code> = 2, returns 2. <code>items</code> = 3, returns 4.
   */
  public static int ceilToPowerOf2(int items) {
    int highest = Integer.highestOneBit(items);
    return items == highest ? items : highest * 2;
  }

  /**
   * Allocates new hash tree represented as byte[][]
   * @param leafs number of leafs in this tree
   * @return allocated tree filled with nulls
   */
  public static byte[][] newTree(int leafs) {
    int size = ceilToPowerOf2(leafs * 2) - 1;
    byte[][] list = new byte[size][];

    for (int i = 0; i < size; i++) {
      list[i] = null;
    }

    return list;
  }


  /**
   * @return merkle root hash
   */
  public byte[] root() {
    return tree[0];
  }

  /**
   * Calculates merkle tree given leafs.
   * @param level the bottom-most level of the tree (e.g. leafs)
   */
  public void create(List<byte[]> level) {
    if (level.isEmpty()) {
      throw new IllegalArgumentException("tree can be calculated from at least one item");
    }

    tree = newTree(level.size());

    List<byte[]> nextLevel = new ArrayList<>(tree.length);

    while (!level.isEmpty()) {
      int leftmostLevelNode = ceilToPowerOf2(level.size()) - 1;
      System.arraycopy(level.toArray(), 0, tree, leftmostLevelNode, level.size());

      // calculate next level
      while (level.size() > 1) {
        // we can get 2 elements
        nextLevel.add(
            hash(level.remove(0), level.remove(0))
        );
      }

      if (level.size() == 1) {
        nextLevel.add(
            level.remove(0)
        );
      }

      if(nextLevel.size() == 1){
        // this is root
        tree[0] = nextLevel.remove(0);
        break;
      }

      // level is clear at this point
      assert level.isEmpty();
      level.addAll(nextLevel);
      nextLevel.clear();
    }

  }
}
