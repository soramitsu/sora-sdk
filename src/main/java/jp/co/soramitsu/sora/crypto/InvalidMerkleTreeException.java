package jp.co.soramitsu.sora.crypto;

import javax.xml.bind.DatatypeConverter;

public class InvalidMerkleTreeException extends Exception {

  public InvalidMerkleTreeException(Hash expectedRoot, Hash actualRoot) {
    super(String.format("Merkle tree is invalid. Expected root %s, actual %s",
        DatatypeConverter.printHexBinary(expectedRoot.getData()),
        DatatypeConverter.printHexBinary(actualRoot.getData())
    ));
  }

  public InvalidMerkleTreeException(int pos){
    super("No element at position " + pos);
  }
}
