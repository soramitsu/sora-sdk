package jp.co.soramitsu.sora.crypto;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.Setter;
import org.spongycastle.util.Arrays;

public class MerkleTree {

  @Setter
  private MessageDigest digest;

  private boolean created = false;

  // [root] [intermediate nodes] [leafs]
  protected byte[][] tree;

  protected byte[] hash(byte[] a, byte[] b) {
    return digest.digest(Arrays.concatenate(a, b));
  }

  public static int getTreeSize(int items) {
    int highest = Integer.highestOneBit(items);
    return items == highest ? items : highest * 2;
  }

  public static byte[][] newTree(int leafs) {
    int size = getTreeSize(leafs);
    byte[][] list = new byte[size][];

    for (int i = 0; i < size; i++) {
      list[i] = null;
    }

    return list;
  }

  public MerkleTree(List<byte[]> leafs, MessageDigest digest) {
    this.digest = digest;

    create(leafs);
  }

  public MerkleTree(MessageDigest digest) {
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

    List<byte[]> nextLevel = new ArrayList<>(tree.length / 2);

    while (!level.isEmpty()) {
      System.arraycopy(level.toArray(), 0, tree, level.size() - 1, level.size());

      // calculate next level
      while (level.size() > 1) {

        if (level.size() > 1) {
          // we can get 2 elements
          nextLevel.add(
              hash(level.remove(0), level.remove(0))
          );
        } else {
          // there is only one element
          nextLevel.add(
              level.remove(0)
          );
        }
      }

      level.clear();
      level.addAll(nextLevel);
      nextLevel.clear();
    }

    created = true;
  }
}
