package jp.co.soramitsu.sora.sdk;

import static com.jayway.jsonpath.JsonPath.read;
import static java.nio.file.Files.readAllBytes;
import static org.junit.Assert.assertEquals;
import static org.spongycastle.util.encoders.Hex.toHexString;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import jp.co.soramitsu.jackson.OneCodeMapper;
import jp.co.soramitsu.sora.sdk.did.model.dto.DDO;
import org.junit.Test;

public class DdoSerializationTest {

  private ObjectMapper mapper = new OneCodeMapper();

  @Test
  public void jsonDDO_correctSerializationOfBytesType() throws IOException, URISyntaxException {

    URI uriToFile =
        this.getClass().getClassLoader().getResource("json/ddo/canonical-1.json").toURI();
    String jsonDDO = new String(readAllBytes(Paths.get(uriToFile)));

    String publicKey = read(jsonDDO, "$.publicKey[0].publicKey");
    String signature = read(jsonDDO, "$.proof.signatureValue");
    DDO ddo = mapper.readValue(jsonDDO, DDO.class);

    String actualPubKey = toHexString(ddo.getPublicKey().get(0).getPublicKey());
    assertEquals(
        "Incorrect serialization of public key value", publicKey.length(), actualPubKey.length());

    String actualSignature = toHexString(ddo.getProof().getSignatureValue());
    assertEquals(
        "Incorrect serialization of proof's signature",
        signature.length(),
        actualSignature.length());
  }
}
