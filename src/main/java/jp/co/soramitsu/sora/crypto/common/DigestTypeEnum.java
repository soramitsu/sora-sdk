package jp.co.soramitsu.sora.crypto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DigestTypeEnum {
  SHA3_256(Consts.SHA3_256, "SHA3-256", "BC"),
  SHA3_512(Consts.SHA3_512, "SHA3-512", "BC");

  @Getter
  private final String type;
  @Getter
  private final String algorithm;
  @Getter
  private final String provider;
}
