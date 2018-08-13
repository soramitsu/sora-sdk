package jp.co.soramitsu.sora.crypto.type;

import jp.co.soramitsu.crypto.ed25519.EdDSAKey;
import jp.co.soramitsu.crypto.ed25519.EdDSASecurityProvider;
import jp.co.soramitsu.sora.crypto.common.Consts;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum KeyTypeEnum {
  Ed25519Sha3(
      Consts.ED25519_SHA3_VERIFICATION_KEY,
      EdDSAKey.KEY_ALGORITHM,
      EdDSASecurityProvider.PROVIDER_NAME
  );

  String keyType;
  String algorithm;
  String provider;

}
