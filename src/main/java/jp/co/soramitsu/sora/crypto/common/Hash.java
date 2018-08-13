package jp.co.soramitsu.sora.crypto.common;

import javax.xml.bind.DatatypeConverter;
import lombok.Value;

@Value
public class Hash {

  byte[] data;

  @Override
  public String toString() {
    return DatatypeConverter.printHexBinary(data);
  }
}
