package jp.co.soramitsu.sora.common;

import java.security.MessageDigest;

public class MockSumMessageDigest extends MessageDigest {

  public static final String ALGORITHM = "algorithm";

  public MockSumMessageDigest() {
    super(ALGORITHM);
  }

  public byte[] digest(byte[] b) {
    assert b.length == 2;

    return new byte[]{(byte) (b[0] + b[1])};
  }

  @Override
  protected void engineUpdate(byte input) {
  }

  @Override
  protected void engineUpdate(byte[] input, int offset, int len) {

  }

  @Override
  protected byte[] engineDigest() {
    return null;
  }

  @Override
  protected void engineReset() {
  }

  @Override
  public String toString(){
    return "<mock of digest algorithm>";
  }
}
