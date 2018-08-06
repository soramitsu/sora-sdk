package jp.co.soramitsu.sora.crypto.signature.suite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Signature;
import java.security.SignatureException;
import jp.co.soramitsu.crypto.ed25519.EdDSAPrivateKey;
import jp.co.soramitsu.crypto.ed25519.EdDSAPublicKey;
import jp.co.soramitsu.sora.crypto.common.SecurityProvider;
import jp.co.soramitsu.sora.crypto.common.SignatureTypeEnum;
import jp.co.soramitsu.sora.crypto.proof.Options;
import jp.co.soramitsu.sora.crypto.service.JSONCanonizer;
import jp.co.soramitsu.sora.crypto.service.JSONSigner;
import jp.co.soramitsu.sora.crypto.service.JSONVerifier;
import jp.co.soramitsu.sora.crypto.service.impl.JSONCanonizerWithOneCoding;
import jp.co.soramitsu.sora.crypto.service.impl.JSONSignerImpl;
import jp.co.soramitsu.sora.crypto.service.impl.JSONVerifierImpl;
import lombok.SneakyThrows;


public class JSONEd25519Sha3SignatureSuite {

  private final JSONSigner signer;
  private final JSONVerifier verifier;

  private Signature signature;

  /**
   * Default constructor for Ed25519 with Sha3 signature algorithm, OneCoding as canonicalization
   * algorithm, and default JSON ObjectMapper.
   */
  public JSONEd25519Sha3SignatureSuite() {
    this(new SecurityProvider(), new JSONCanonizerWithOneCoding(), new ObjectMapper());
  }

  /**
   * Constructor for mocks injection.
   *
   * @param provider security provider. Mockable non-static wrapper for static Signature methods.
   * @param canonizer canonicalization algorithm.
   * @param mapper JSON ObjectMapper
   */
  @SneakyThrows
  public JSONEd25519Sha3SignatureSuite(
      SecurityProvider provider,
      JSONCanonizer canonizer,
      ObjectMapper mapper
  ) {
    this.signer = new JSONSignerImpl(canonizer, mapper);
    this.verifier = new JSONVerifierImpl(canonizer, mapper);
    this.signature = provider.getSignature(SignatureTypeEnum.Ed25519Sha3Signature);
  }

  /**
   * Sign Object and return signed JSON.
   *
   * @param object JsonNode, Map<String, Object> or POJO; i.e. anything that can be processed with
   * ObjectMapper
   * @param privateKey EdDSA private key
   * @param options signature options
   * @return signed JSON with key "proof". Previous proofs will be erased.
   * @throws IOException when input JSON can not be processed
   */
  @SneakyThrows({SignatureException.class, InvalidKeyException.class})  // never thrown
  // these exceptions are never thrown
  public ObjectNode sign(Object object, EdDSAPrivateKey privateKey, Options options)
      throws IOException {
    signature.initSign(privateKey);
    return signer.sign(object, signature, options);
  }

  /**
   * Verify signed Object
   *
   * @param root JsonNode, Map<String, Object> or POJO; i.e. anything that can be processed with
   * ObjectMapper
   * @param publicKey EdDSA public key
   * @return true if <code>root</code> has cryptographically valid "proof" node; false otherwise.
   * @throws IOException when input JSON can not be processed
   */
  @SneakyThrows({SignatureException.class, InvalidKeyException.class})  // never thrown
  public boolean verify(Object root, EdDSAPublicKey publicKey)
      throws IOException {
    signature.initVerify(publicKey);
    return verifier.verify(root, signature);
  }
}
