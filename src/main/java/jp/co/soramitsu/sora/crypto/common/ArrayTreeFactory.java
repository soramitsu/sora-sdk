package jp.co.soramitsu.sora.crypto.common;

import static jp.co.soramitsu.sora.crypto.common.Util.ceilToPowerOf2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayTreeFactory {

  private ArrayTreeFactory() {
  }

  public static <T> ArrayTree<T> createWithNLeafs(int leafs) {
    if (leafs <= 0) {
      throw new InvalidNodeNumberException(leafs);
    }

    int size = ceilToPowerOf2(leafs * 2) - 1;
    return createWithCapacity(size);
  }

  /**
   * Creates new ArrayTree with given number of total elements
   *
   * @param capacity number of leafs in this tree
   * @return allocated tree filled with nulls
   */
  public static <T> ArrayTree<T> createWithCapacity(int capacity) {
    return new ArrayTree<>(
        new ArrayList<>(
            Collections.nCopies(capacity, null)
        )
    );
  }

  public static <T> ArrayTree<T> createFromNodes(List<T> nodes) {
    return new ArrayTree<>(nodes);
  }

}
