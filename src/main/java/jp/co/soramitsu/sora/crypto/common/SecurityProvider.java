package jp.co.soramitsu.sora.crypto.common;

import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.Security;
import java.security.Signature;
import jp.co.soramitsu.crypto.ed25519.EdDSASecurityProvider;
import jp.co.soramitsu.sora.crypto.type.DigestTypeEnum;
import jp.co.soramitsu.sora.crypto.type.KeyTypeEnum;
import jp.co.soramitsu.sora.crypto.type.SignatureTypeEnum;
import lombok.SneakyThrows;
import org.spongycastle.jce.provider.BouncyCastleProvider;


/**
 * The purpose of this class is to be a wrapper over static `getInstance` methods, so these static
 * methods can be mocked.
 */
public class SecurityProvider {

  static {
    Security.addProvider(new EdDSASecurityProvider()); // for ed25519-sha3 crypto
    Security.addProvider(new BouncyCastleProvider());  // for sha3 hash
  }

  @SneakyThrows // method is guaranteed to NOT throw
  public Signature getSignature(SignatureTypeEnum type) {
    return Signature.getInstance(type.getAlgorithm(), type.getProvider());
  }

  @SneakyThrows
  public MessageDigest getMessageDigest(DigestTypeEnum type) {
    return MessageDigest.getInstance(type.getAlgorithm(), type.getProvider());
  }

  @SneakyThrows
  public KeyPairGenerator getKeyPairGenerator(KeyTypeEnum type) {
    return KeyPairGenerator.getInstance(type.getAlgorithm(), type.getProvider());
  }
}
