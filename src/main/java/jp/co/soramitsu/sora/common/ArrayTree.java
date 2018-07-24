package jp.co.soramitsu.sora.common;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Value;

@Value
public class ArrayTree<E> {

  private List<E> tree;

  public ArrayTree(List<E> tree) {
    if (Util.ceilToPowerOf2(tree.size() + 1) != tree.size() + 1) {
      throw new InvalidNodeNumberException(tree.size());
    }

    this.tree = tree;
  }


  public List<E> getLeaves() {
    int leafsTotal = (tree.size() + 1) / 2;
    int leftmostLeafIndex = leafsTotal - 1;
    return tree.stream()
        .skip(leftmostLeafIndex)
        .collect(Collectors.toList());
  }

  /**
   * @param i element position in the tree
   * @return true, if node exists (has non-null value); false otherwise
   */
  public boolean hasNodeAt(int i) {
    return i >= 0 && i < tree.size() && get(i) != null;
  }

  /**
   * Returns index of element in the tree
   *
   * @param el element to be found
   * @return index in the tree if found; -1 otherwise
   */
  public int indexOf(E el) {
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

  /**
   * Given parent index, get left child index in the binary tree.
   *
   * Can be used only with trees, where all siblings exist (this tree).
   *
   * @param parent parent index
   * @return left child index
   */
  public static int getLeftChildIndex(int parent) {
    return parent * 2 + 1;
  }

  /**
   * Given parent index, get right child index in the binary tree.
   *
   * Can be used only with trees, where all siblings exist (this tree).
   *
   * @param parent parent index
   * @return right child index
   */
  public static int getRightChildIndex(int parent) {
    return parent * 2 + 2;
  }

  /**
   * Returns parent index in the tree.
   *
   * Can be used only with trees, where all siblings exist (this tree).
   *
   * @param child child index
   * @return parent index or -1, if no parent (it is root)
   */
  public static int getParentIndex(int child) {
    if (child <= 0) {
      return -1;
    }

    return (child - 1) / 2;
  }

  /**
   * Get neighbour index of node with position = <code>pos</code>
   *
   * Can be used only with trees, where all siblings exist (this tree).
   *
   * @param pos position of the node
   * @return -1 if pos is invalid; 0 if pos is root; right child if pos is left child and vice versa
   */
  public static int getNeighborIndex(int pos) {
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
