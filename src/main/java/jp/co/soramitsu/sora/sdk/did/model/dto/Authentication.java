package jp.co.soramitsu.sora.sdk.did.model.dto;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.MINIMAL_CLASS;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jp.co.soramitsu.sora.sdk.did.model.Executable;
import jp.co.soramitsu.sora.sdk.did.model.dto.authentication.AuthenticationExecutor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonTypeInfo(use = MINIMAL_CLASS, property = "type")
@EqualsAndHashCode
@ToString
public abstract class Authentication implements Executable<AuthenticationExecutor> {

  public abstract DID getPublicKey();
}
