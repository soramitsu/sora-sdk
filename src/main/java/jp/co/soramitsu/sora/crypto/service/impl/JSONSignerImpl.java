package jp.co.soramitsu.sora.crypto.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.Signature;
import java.security.SignatureException;
import javax.xml.bind.DatatypeConverter;
import jp.co.soramitsu.sora.common.Util;
import jp.co.soramitsu.sora.crypto.common.Consts;
import jp.co.soramitsu.sora.crypto.proof.Options;
import jp.co.soramitsu.sora.crypto.proof.Proof;
import jp.co.soramitsu.sora.crypto.service.JSONCanonizer;
import jp.co.soramitsu.sora.crypto.service.JSONSigner;
import lombok.AllArgsConstructor;
import lombok.NonNull;


@AllArgsConstructor
public class JSONSignerImpl implements JSONSigner {

  private final JSONCanonizer canonizer;
  private final ObjectMapper mapper;

  @Override
  public ObjectNode sign(
      @NonNull Object root,
      @NonNull Signature signature,
      @NonNull Options options
  ) throws SignatureException, IOException {

    String type = options.getType().getAlgorithm();
    if (!type.equals(signature.getAlgorithm())) {
      throw new SignatureException(
          String
              .format("options (%s) and signature (%s) have different signature algorithms", type,
                  signature.getAlgorithm())
      );
    }

    ObjectNode jsonDocument = mapper.valueToTree(root);
    ObjectNode output = Util.deepCopyWithoutProofNode(jsonDocument);

    // hash input json and sign this hash
    byte[] prepared = Util.serializeWithOptions(this.canonizer, output, options);
    signature.update(prepared);
    byte[] sig = signature.sign();

    // insert "proof" node into json
    Proof proof = new Proof(options, sig);
    JsonNode proofNode = mapper.valueToTree(proof);
    output.set(Consts.PROOF_KEY, proofNode);

    // return signed JSON
    return output;
  }
}
