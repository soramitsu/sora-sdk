package jp.co.soramitsu.sora.crypto.common;

import java.security.SecureRandom;
import javax.xml.bind.DatatypeConverter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class HexdigestSaltGenerator implements SaltGenerator {

  private SecureRandom random = new SecureRandom();
  private int length = 8 /* bytes */;

  @Override
  public String next() {
    byte[] salt = new byte[length];
    random.nextBytes(salt);
    return DatatypeConverter.printHexBinary(salt);
  }
}
