package jp.co.soramitsu.sora.common;

public class Util {

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
  public static ByteArrayTree allocateEmptyTree(int leafs) {
    int size = ceilToPowerOf2(leafs * 2) - 1;
    return new ByteArrayTree(size);
  }

}
