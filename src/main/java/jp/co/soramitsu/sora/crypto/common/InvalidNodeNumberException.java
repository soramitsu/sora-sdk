package jp.co.soramitsu.sora.crypto.common;

public class InvalidNodeNumberException extends RuntimeException {

  public InvalidNodeNumberException(int leafs) {
    super("Number of nodes must be positive and be a (power of 2) - 1: " + leafs);
  }
}
