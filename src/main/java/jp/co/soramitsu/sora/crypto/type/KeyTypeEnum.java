package jp.co.soramitsu.sora.crypto.type;

import jp.co.soramitsu.crypto.ed25519.EdDSAKey;
import jp.co.soramitsu.crypto.ed25519.EdDSASecurityProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum KeyTypeEnum {
  Ed25519Sha3VerificationKey(
      EdDSAKey.KEY_ALGORITHM,
      EdDSASecurityProvider.PROVIDER_NAME
  );

  String algorithm;
  String provider;

}
