package jp.co.soramitsu.sora.sdk.model.dto.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.soramitsu.sora.sdk.model.dto.Authentication;
import jp.co.soramitsu.sora.sdk.model.dto.DID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Ed25519Sha3Authentication extends Authentication {

  private DID publicKey;

  @JsonCreator
  public Ed25519Sha3Authentication(@JsonProperty("publicKey") DID publicKey) {
    super(publicKey);
  }

  @Override
  public void visit(AuthenticationVisitor visitor) {
    visitor.visit(this);
  }
}
