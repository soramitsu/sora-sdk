package jp.co.soramitsu.sora.sdk.model.type;

import com.fasterxml.jackson.annotation.JsonValue;
import jp.co.soramitsu.sora.sdk.crypto.common.Consts;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.spongycastle.jce.provider.BouncyCastleProvider;

@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum DigestTypeEnum {
  SHA3_256(Consts.SHA3_256, "SHA3-256", BouncyCastleProvider.PROVIDER_NAME),
  SHA3_512(Consts.SHA3_512, "SHA3-512", BouncyCastleProvider.PROVIDER_NAME);

  @JsonValue
  String type;
  String algorithm;
  String provider;
}
