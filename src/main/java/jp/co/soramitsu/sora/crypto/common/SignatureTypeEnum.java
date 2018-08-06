package jp.co.soramitsu.sora.crypto.common;

import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.soramitsu.crypto.ed25519.EdDSAEngine;
import jp.co.soramitsu.crypto.ed25519.EdDSASecurityProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignatureTypeEnum {
  Ed25519Sha3Signature(
      Consts.ED25519_SHA3_SIGNATURE,
      Consts.ED25519_SHA3_VERIFICATION_KEY,
      EdDSAEngine.SIGNATURE_ALGORITHM,
      EdDSASecurityProvider.PROVIDER_NAME
  );

  @JsonValue
  private final String signatureType;
  private final String keyType;
  private final String algorithm;
  private final String provider;

}
