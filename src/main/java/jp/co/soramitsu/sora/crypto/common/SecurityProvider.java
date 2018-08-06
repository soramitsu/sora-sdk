package jp.co.soramitsu.sora.crypto.common;

import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import jp.co.soramitsu.crypto.ed25519.EdDSASecurityProvider;
import org.spongycastle.jce.provider.BouncyCastleProvider;


/**
 * The purpose of this class is to be a wrapper over static `getInstance` methods, so these static
 * methods can be mocked.
 */
public class SecurityProvider {

  private static void addProviderOnce(Provider provider) {
    if (Security.getProvider(provider.getName()) == null) {
      Security.addProvider(provider);
    }
  }

  static {
    addProviderOnce(new EdDSASecurityProvider()); // for ed25519-sha3 crypto
    addProviderOnce(new BouncyCastleProvider());  // for sha3 hash
  }

  public Signature getSignature(SignatureTypeEnum type)
      throws NoSuchAlgorithmException, NoSuchProviderException {
    return Signature.getInstance(type.getAlgorithm(), type.getProvider());
  }

  public MessageDigest getMessageDigest(DigestTypeEnum type)
      throws NoSuchProviderException, NoSuchAlgorithmException {
    return MessageDigest.getInstance(type.getAlgorithm(), type.getProvider());
  }

  public KeyPairGenerator getKeyPairGenerator(SignatureTypeEnum type)
      throws NoSuchProviderException, NoSuchAlgorithmException {
    return KeyPairGenerator.getInstance(type.getAlgorithm(), type.getProvider());
  }
}
