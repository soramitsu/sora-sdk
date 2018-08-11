package jp.co.soramitsu.sora.crypto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum DigestTypeEnum {
  SHA3_256(Consts.SHA3_256, "SHA3-256", "BC"),
  SHA3_512(Consts.SHA3_512, "SHA3-512", "BC");

  String type;
  String algorithm;
  String provider;
}
