package jp.co.soramitsu.sora.crypto.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.Signature;
import java.security.SignatureException;
import jp.co.soramitsu.sora.crypto.common.Consts;
import jp.co.soramitsu.sora.crypto.common.Util;
import jp.co.soramitsu.sora.crypto.proof.Options;
import jp.co.soramitsu.sora.crypto.proof.Proof;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JSONVerifierImpl implements JSONVerifier {

  JSONCanonizer canonizer;
  ObjectMapper mapper;

  @Override
  public boolean verify(Object object, Signature signature)
      throws IOException, SignatureException {
    ObjectNode root = mapper.valueToTree(object);

    if (!root.hasNonNull(Consts.PROOF_KEY)) {
      return false;
    }

    JsonNode proofNode = root.get(Consts.PROOF_KEY);
    Proof proof = mapper.treeToValue(proofNode, Proof.class);
    Options options = new Options(
        proof.getType(),
        proof.getCreated(),
        proof.getCreator(),
        proof.getNonce(),
        proof.getPurpose()
    );

    Util.verifyAlgorithmType(options, signature);

    ObjectNode out = Util.deepCopyWithoutProofNode(root);
    byte[] prepared = Util.serializeWithOptions(this.canonizer, out, options);

    signature.update(prepared); // throws SignatureException
    return signature.verify(proof.getSignatureValue());
  }
}
