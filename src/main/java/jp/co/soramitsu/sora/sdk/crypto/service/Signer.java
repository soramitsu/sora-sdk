package jp.co.soramitsu.sora.sdk.crypto.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.SignatureException;

public interface Signer<T> {

  T sign(T ddo, PrivateKey privateKey) throws IOException, SignatureException, InvalidKeyException;
}
