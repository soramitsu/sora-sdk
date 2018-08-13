package jp.co.soramitsu.sora.crypto.type;

import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.soramitsu.crypto.ed25519.EdDSAEngine;
import jp.co.soramitsu.crypto.ed25519.EdDSASecurityProvider;
import jp.co.soramitsu.sora.crypto.common.Consts;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum SignatureTypeEnum {
  Ed25519Sha3Signature(
      Consts.ED25519_SHA3_SIGNATURE,
      KeyTypeEnum.Ed25519Sha3,
      EdDSAEngine.SIGNATURE_ALGORITHM,
      EdDSASecurityProvider.PROVIDER_NAME
  );

  @JsonValue
  String signatureType;
  KeyTypeEnum keyType;
  String algorithm;
  String provider;

}
