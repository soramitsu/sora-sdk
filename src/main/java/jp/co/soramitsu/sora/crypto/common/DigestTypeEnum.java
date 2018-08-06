package jp.co.soramitsu.sora.crypto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DigestTypeEnum {
  SHA3_256(Consts.SHA3_256, "SHA3-256", "BC"),
  SHA3_512(Consts.SHA3_512, "SHA3-512", "BC");

  private final String type;
  private final String algorithm;
  private final String provider;
}
