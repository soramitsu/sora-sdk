package jp.co.soramitsu.sora.sdk.common;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

public class MockSignature extends Signature {

  private byte[] signature;

  public MockSignature(String algorithm, byte[] signature) {
    super(algorithm);

    this.signature = signature;
  }

  @Override
  protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
    /* do nothing */
  }

  @Override
  protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
    /* do nothing */
  }

  @Override
  protected void engineUpdate(byte b) throws SignatureException {
    /* do nothing */
  }

  @Override
  protected void engineUpdate(byte[] b, int off, int len) throws SignatureException {
    /* do nothing */
  }

  @Override
  protected byte[] engineSign() throws SignatureException {
    return signature;
  }

  @Override
  protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
    return Arrays.equals(sigBytes, signature);
  }

  @Override
  protected void engineSetParameter(String param, Object value) throws InvalidParameterException {
    /* do nothing */
  }

  @Override
  protected Object engineGetParameter(String param) throws InvalidParameterException {
    return null;
  }
}
