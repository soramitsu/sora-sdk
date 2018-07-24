package jp.co.soramitsu.sora.crypto;

import lombok.Value;

@Value
public class MerkleNode {

  int position;

  Hash hash;
}
