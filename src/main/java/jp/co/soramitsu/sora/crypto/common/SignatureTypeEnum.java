package jp.co.soramitsu.sora.crypto.common;

import com.fasterxml.jackson.annotation.JsonCreator;
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

//  @JsonCreator
//  public static SignatureTypeEnum fromString(String v) {
//    for (SignatureTypeEnum e : values()) {
//      if (e.signatureType.equals(v)) {
//        return e;
//      }
//    }
//    throw new IllegalArgumentException("invalid string value passed: " + v);
//  }
}
