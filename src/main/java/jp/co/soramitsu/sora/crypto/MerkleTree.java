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

  public static byte[] hash(MessageDigest d, byte[] a, byte[] b) {
    return d.digest(Arrays.concatenate(a, b));
  }

  public byte[][] getTree() {
    return tree;
  }

  public static int ceilToPowerOf2(int items) {
    int highest = Integer.highestOneBit(items);
    return items == highest ? items : highest * 2;
  }

  public static byte[][] newTree(int leafs) {
    int size = ceilToPowerOf2(leafs * 2) - 1;
    byte[][] list = new byte[size][];

    for (int i = 0; i < size; i++) {
      list[i] = null;
    }

    return list;
  }

  public MerkleTree(@NonNull MessageDigest digest, @NonNull List<byte[]> leafs) {
    this.digest = digest;

    create(leafs);
  }

  public MerkleTree(@NonNull MessageDigest digest) {
    this.digest = digest;
  }

  public byte[] root() {
    return tree[0];
  }

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
            hash(digest, level.remove(0), level.remove(0))
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
