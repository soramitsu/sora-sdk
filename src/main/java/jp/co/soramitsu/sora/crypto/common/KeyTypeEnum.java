package jp.co.soramitsu.sora.crypto.common;

import jp.co.soramitsu.crypto.ed25519.EdDSAKey;
import jp.co.soramitsu.crypto.ed25519.EdDSASecurityProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KeyTypeEnum {
  Ed25519Sha3(
      Consts.ED25519_SHA3_VERIFICATION_KEY,
      EdDSAKey.KEY_ALGORITHM,
      EdDSASecurityProvider.PROVIDER_NAME
  );

  private String keyType;
  private String algorithm;
  private String provider;

}
