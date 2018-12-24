package jp.co.soramitsu.sora.sdk.did.model.dto.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.soramitsu.sora.sdk.did.model.dto.Authentication;
import jp.co.soramitsu.sora.sdk.did.model.dto.DID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Ed25519Sha3Authentication extends Authentication {

  private DID publicKey;

  @JsonCreator
  public Ed25519Sha3Authentication(@JsonProperty("publicKey") DID publicKey) {
    this.publicKey = publicKey;
  }

  @Override
  public void accept(AuthenticationExecutor visitor) {
    visitor.execute(this);
  }
}
