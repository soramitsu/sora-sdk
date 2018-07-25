package jp.co.soramitsu.sora.common;

import java.util.Arrays;
import java.util.Random;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MockRandom extends Random {

  private byte fixed;

  public void nextBytes(byte[] b) {
    Arrays.fill(b, fixed);
  }
}
