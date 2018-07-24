package jp.co.soramitsu.sora.common;

import java.util.List;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
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
    return tree
        .stream()
        .skip(leftmostLeafIndex)
        .collect(Collectors.toList());
  }

  public int find(E el) {
    return tree.indexOf(el);
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
    if (child <= 0) {
      return -1;
    }

    return (child - 1) / 2;
  }

  /**
   * Get neighbour of node with position = <code>pos</code>
   *
   * @param pos position of the node
   * @return -1 if pos is invalid; 0 if pos is root; right child if pos is left child and vice versa
   */
  public static int getNeighbor(int pos) {
    if (pos < 0) {
      return -1;
    } else if (pos == 0) {
      return 0;
    } else if (pos % 2 == 0) {
      return pos - 1;
    } else {
      return pos + 1;
    }
  }
}
