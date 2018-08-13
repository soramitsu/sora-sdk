package jp.co.soramitsu.sora.crypto.common;

import java.util.Random;
import javax.xml.bind.DatatypeConverter;
import lombok.Value;

@Value
public class HexdigestSaltGenerator implements SaltGenerator {

  private Random random;
  private int length;

  @Override
  public String next() {
    byte[] salt = new byte[length];
    random.nextBytes(salt);
    return DatatypeConverter.printHexBinary(salt);
  }
}
