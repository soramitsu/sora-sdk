package jp.co.soramitsu.sora.common;

import java.util.Arrays;
import lombok.Getter;

public class ByteArrayTree {

  @Getter
  private byte[][] tree;

  public ByteArrayTree(int capacity) {
    tree = new byte[capacity][];
  }

  public ByteArrayTree(byte[][] tree) {
    this.tree = tree;
  }

  /**
   * Finds element in the tree.
   *
   * @return position in the tree if found, -1 if not found.
   */
  public int find(byte[] element) {
    for (int i = 0; i < tree.length; i++) {
      if (Arrays.equals(tree[i], element)) {
        return i;
      }
    }

    return -1;
  }

  public boolean hasElement(int i) {
    return i < tree.length && get(i) != null;
  }

  public byte[][] getLeafs() {
    int leafsTotal = (tree.length + 1) / 2;
    int leftmostLeafIndex = leafsTotal - 1;
    return Arrays.copyOfRange(tree, leftmostLeafIndex, tree.length);
  }

  public int size() {
    return tree.length;
  }

  public byte[] get(int i) {
    return tree[i];
  }

  public void set(int i, byte[] el) {
    tree[i] = el;
  }

  public static int getLeftChild(int parent) {
    return parent * 2 + 1;
  }

  public static int getRightChild(int parent) {
    return parent * 2 + 2;
  }

  /**
   * @param child child index
   * @return parent index or -1, if no parent (root)
   */
  public static int getParent(int child) {
    int p = (child - 1) / 2;
    if (child <= 0) {
      return -1;
    }
    return p;
  }
}
