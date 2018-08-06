package jp.co.soramitsu.sora.crypto;

import static org.junit.Assert.fail;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.Signature;
import jp.co.soramitsu.crypto.ed25519.EdDSAEngine;
import jp.co.soramitsu.crypto.ed25519.EdDSASecurityProvider;
import org.junit.Test;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class CryptoProvidersTest {

  @Test
  public void hasEd25519WithSha3() throws NoSuchProviderException, NoSuchAlgorithmException {
    try {
      Security.addProvider(new EdDSASecurityProvider());

      // no exception thrown
      Signature.getInstance(EdDSAEngine.SIGNATURE_ALGORITHM, EdDSASecurityProvider.PROVIDER_NAME);
      Signature.getInstance(EdDSAEngine.SIGNATURE_ALGORITHM);
      KeyPairGenerator.getInstance("EdDSA", "EdDSA");
      KeyFactory.getInstance("EdDSA", "EdDSA");
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void hasSha3() throws NoSuchAlgorithmException, NoSuchProviderException {
    try {
      Security.addProvider(new BouncyCastleProvider());

      // no exception thrown
      MessageDigest.getInstance("SHA3-256", BouncyCastleProvider.PROVIDER_NAME);
      MessageDigest.getInstance("SHA3-256");
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}
