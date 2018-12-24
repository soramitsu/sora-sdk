package jp.co.soramitsu.sora.sdk.did.model.dto;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jp.co.soramitsu.sora.sdk.did.model.Executable;
import jp.co.soramitsu.sora.sdk.did.model.dto.authentication.AuthenticationExecutor;
import jp.co.soramitsu.sora.sdk.did.model.dto.authentication.Ed25519Sha3Authentication;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonTypeInfo(use = NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Ed25519Sha3Authentication.class, name = "Ed25519Sha3Authentication")
})
@EqualsAndHashCode
@ToString
public abstract class Authentication implements Executable<AuthenticationExecutor> {

  public abstract DID getPublicKey();
}
