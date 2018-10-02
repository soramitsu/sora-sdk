package jp.co.soramitsu.sora.sdk.crypto.common;

import static org.spongycastle.util.encoders.Hex.toHexString;

import java.security.SecureRandom;
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
    return toHexString(salt);
  }
}
