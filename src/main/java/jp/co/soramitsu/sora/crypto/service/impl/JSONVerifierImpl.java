package jp.co.soramitsu.sora.crypto.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.Signature;
import java.security.SignatureException;
import jp.co.soramitsu.sora.common.Util;
import jp.co.soramitsu.sora.crypto.common.Consts;
import jp.co.soramitsu.sora.crypto.proof.Proof;
import jp.co.soramitsu.sora.crypto.service.JSONCanonizer;
import jp.co.soramitsu.sora.crypto.service.JSONVerifier;
import lombok.Value;


@Value
public class JSONVerifierImpl implements JSONVerifier {

  private final JSONCanonizer canonizer;
  private final ObjectMapper mapper;

  @Override
  public boolean verify(Object object, Signature signature)
      throws IOException, SignatureException {
    ObjectNode root = mapper.valueToTree(object);

    if (!root.hasNonNull(Consts.PROOF_KEY)) {
      return false;
    }

    // read proof
    JsonNode proofNode = root.get(Consts.PROOF_KEY);
    Proof proof = mapper.treeToValue(proofNode, Proof.class);

    // sanitize and serialize JSON
    ObjectNode out = Util.deepCopyWithoutProofNode(root);
    byte[] prepared = Util.serializeWithOptions(this.canonizer, out, proof);

    // verify signature
    signature.update(prepared); // throws SignatureException
    return signature.verify(proof.getSignatureValue());
  }
}
