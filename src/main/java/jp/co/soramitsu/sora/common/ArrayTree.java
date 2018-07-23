package jp.co.soramitsu.sora.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

public class ArrayTree<E> {

  @Getter
  private List<E> tree;

  public ArrayTree(List<E> tree) {
    this.tree = tree;
  }

  public boolean hasElement(int i) {
    return i < tree.size() && get(i) != null;
  }

  public List<E> getLeafs() {
    int leafsTotal = (tree.size() + 1) / 2;
    int leftmostLeafIndex = leafsTotal - 1;
    return tree.stream().skip(leftmostLeafIndex).collect(Collectors.toList());
  }

  public int size() {
    return tree.size();
  }

  public E get(int i) {
    return tree.get(i);
  }

  public void set(int i, E el) {
    tree.set(i, el);
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

  @Override
  public String toString() {
    return tree.toString();
  }

  public boolean equals(ArrayTree<E> other) {
    return tree.equals(other.tree);
  }
}
