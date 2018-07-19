package jp.co.soramitsu.sora.crypto;

import java.security.MessageDigest;
import jp.co.soramitsu.sora.common.ByteArrayTree;
import lombok.Value;

@Value
public class MerkleTree {

  MessageDigest digest;

  /**
   * This is binary hashTree and it is stored "by levels", so first level is root and stored as hashTree[0],
   * next level (2 items) is stored in hashTree[1] and hashTree[2], and so on.
   *
   * Scheme: [root] [intermediate nodes] [leafs]
   */
  ByteArrayTree hashTree;

  /**
   * @return merkle root hash
   */
  public byte[] root() {
    return hashTree.get(0);
  }
}

