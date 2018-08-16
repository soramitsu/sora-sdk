package jp.co.soramitsu.sora.crypto.common;

import javax.xml.bind.DatatypeConverter;
import lombok.NonNull;
import lombok.Value;

@Value
public class Hash {

  @NonNull
  byte[] data;

  @Override
  public String toString() {
    return DatatypeConverter.printHexBinary(data);
  }

  public static Hash fromHex(String hex) {
    return new Hash(DatatypeConverter.parseHexBinary(hex));
  }
}
