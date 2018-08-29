package jp.co.soramitsu.sora.sdk.crypto.merkle;

import jp.co.soramitsu.sora.sdk.crypto.common.Hash;
import lombok.Value;

@Value
public class MerkleNode {

  int position;

  Hash hash;
}
