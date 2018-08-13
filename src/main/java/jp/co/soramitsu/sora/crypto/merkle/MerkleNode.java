package jp.co.soramitsu.sora.crypto.merkle;

import lombok.Value;

@Value
public class MerkleNode {

  int position;

  Hash hash;
}
