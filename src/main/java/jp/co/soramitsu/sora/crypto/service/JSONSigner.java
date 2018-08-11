package jp.co.soramitsu.sora.crypto.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.Signature;
import java.security.SignatureException;
import jp.co.soramitsu.sora.crypto.proof.Options;


public interface JSONSigner {

  /**
   * Sign input object with given signature algorithm and options.
   *
   * @param object input key-value object. Can be Jackson Tree (JsonNode, ObjectNode...), POJO, or
   * Map<String, Object>
   * @param signature signature algorithm, initialized for signing. Example:
   * <code>
   * Signature sig = Signature.getInstance(...); sig.initSign(privateKey, new SecureRandom())
   * </code>
   * @param options Signature options
   */
  ObjectNode sign(
      Object object,
      Signature signature,
      Options options
  ) throws SignatureException, IOException;
}
