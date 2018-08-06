package jp.co.soramitsu.sora.crypto.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SignatureTypeEnum {
  Ed25519Sha3Signature(
      Consts.ED25519_SHA3_SIGNATURE,
      Consts.ED25519_SHA3_VERIFICATION_KEY,
      "EdDSA", "EdDSA"
  );

  @Getter
  @JsonValue
  private final String signatureType;
  @Getter
  private final String keyType;
  @Getter
  private final String algorithm;
  @Getter
  private final String provider;

}
