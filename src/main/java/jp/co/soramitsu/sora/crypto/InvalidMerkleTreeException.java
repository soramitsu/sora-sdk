package jp.co.soramitsu.sora.crypto;

public class InvalidMerkleTreeException extends RuntimeException {

  public InvalidMerkleTreeException(int pos) {
    super("No element at position: " + pos);
  }
}
