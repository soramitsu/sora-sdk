package jp.co.soramitsu.sora.crypto;

import javax.xml.bind.DatatypeConverter;

public class InvalidMerkleTreeException extends Exception {

  public InvalidMerkleTreeException(byte[] expectedRoot, byte[] actualRoot) {
    super(String.format("Merkle tree is invalid. Expected root %s, actual %s",
        DatatypeConverter.printHexBinary(expectedRoot),
        DatatypeConverter.printHexBinary(actualRoot)
    ));
  }
}
