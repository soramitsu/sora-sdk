package jp.co.soramitsu.sora.sdk.crypto.common;

import javax.xml.bind.DatatypeConverter;
import lombok.NonNull;
import lombok.Value;

@Value
public class Hash {

  @NonNull
  byte[] data;

  public static Hash fromHex(String hex) {
    return new Hash(DatatypeConverter.parseHexBinary(hex));
  }

  @Override
  public String toString() {
    return DatatypeConverter.printHexBinary(data);
  }
}
