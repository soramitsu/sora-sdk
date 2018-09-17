package jp.co.soramitsu.sora.sdk.crypto.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.Signature;
import java.security.SignatureException;
import javax.xml.bind.DatatypeConverter;
import jp.co.soramitsu.sora.sdk.crypto.common.Consts;
import jp.co.soramitsu.sora.sdk.crypto.common.Util;
import jp.co.soramitsu.sora.sdk.did.model.dto.Options;
import jp.co.soramitsu.sora.sdk.did.model.dto.Proof;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)

public class JSONSignerImpl implements JSONSigner {

  JSONCanonizer canonizer;
  ObjectMapper mapper;

  private byte[] createSignature(ObjectNode root, Signature signature, Options options)
      throws SignatureException, IOException {
    // hash input json and sign this hash
    byte[] prepared = Util.serializeWithOptions(this.canonizer, root, options);
    signature.update(prepared);
    return signature.sign();
  }


  private void setProof(ObjectNode output, Proof proof) {
    JsonNode proofNode = mapper.valueToTree(proof);
    output.set(Consts.PROOF_KEY, proofNode);
  }

  @Override
  public ObjectNode sign(
      @NonNull Object root,
      @NonNull Signature signature,
      @NonNull Options options
  ) throws SignatureException, IOException {

    Util.verifyAlgorithmType(options, signature);

    ObjectNode jsonDocument = mapper.valueToTree(root);
    ObjectNode output = Util.deepCopyWithoutProofNode(jsonDocument);

    byte[] sig = createSignature(output, signature, options);

    Proof proof = new Proof(options, sig);
    setProof(output, proof);

    return output;
  }
}
