package jp.co.soramitsu.sora.sdk.common;

import java.security.SecureRandom;
import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MockRandom extends SecureRandom {

  private byte fixed;

  public void nextBytes(byte[] b) {
    Arrays.fill(b, fixed);
  }
}
