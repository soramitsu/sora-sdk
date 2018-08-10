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
      KeyTypeEnum.Ed25519Sha3,
      EdDSAEngine.SIGNATURE_ALGORITHM,
      EdDSASecurityProvider.PROVIDER_NAME
  );

  @JsonValue
  private final String signatureType;
  private final KeyTypeEnum keyType;
  private final String algorithm;
  private final String provider;

}
