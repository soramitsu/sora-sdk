package jp.co.soramitsu.sora.crypto;

import lombok.Value;

@Value
public class MerkleNode {

  int position;

  Hash hash;

  @Override
  public String toString() {
    return String.format("<%d, %s>", position, hash);
  }
}
