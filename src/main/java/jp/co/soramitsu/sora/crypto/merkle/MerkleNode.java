package jp.co.soramitsu.sora.crypto.merkle;

import jp.co.soramitsu.sora.crypto.common.Hash;
import lombok.Value;

@Value
public class MerkleNode {

  int position;

  Hash hash;
}
