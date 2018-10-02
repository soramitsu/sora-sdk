package jp.co.soramitsu.sora.sdk.crypto.common;

import java.security.SecureRandom;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.spongycastle.util.encoders.Hex;

@AllArgsConstructor
@NoArgsConstructor
public class HexdigestSaltGenerator implements SaltGenerator {

  private SecureRandom random = new SecureRandom();
  private int length = 8 /* bytes */;

  @Override
  public String next() {
    byte[] salt = new byte[length];
    random.nextBytes(salt);
    return Hex.toHexString(salt);
  }
}
