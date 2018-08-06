package jp.co.soramitsu.sora.crypto.service;

import java.io.IOException;
import java.security.Signature;
import java.security.SignatureException;

public interface JSONVerifier {

  /**
   * Verifies proof node in the <code>root</code> JSON with <code>publicKey</code>
   *
   * @param root JSON node, that contains "proof" key
   * @return true if proof is valid, false otherwise
   * @throws IllegalArgumentException is thrown when "proof"."type" is not implemented.
   * @throws SignatureException should never be thrown. If thrown, it is evidence of programming
   * error (signature provider is not initialized with sign or verify)
   */
  boolean verify(Object root, Signature signature)
      throws IOException, SignatureException;

}
