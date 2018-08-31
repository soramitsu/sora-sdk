package jp.co.soramitsu.sora.sdk.crypto.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.SignatureException;

public interface Verifier<T> {

  boolean verify(T obj, PublicKey publicKey)
      throws IOException, InvalidKeyException, SignatureException;
}
