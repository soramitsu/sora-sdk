package jp.co.soramitsu.sora.sdk.did.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jp.co.soramitsu.sora.sdk.did.model.Visitable;
import jp.co.soramitsu.sora.sdk.did.model.dto.authentication.AuthenticationVisitor;
import jp.co.soramitsu.sora.sdk.did.model.dto.authentication.Ed25519Sha3Authentication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Ed25519Sha3Authentication.class, name = "Ed25519Sha3Authentication")
})
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public abstract class Authentication implements Visitable<AuthenticationVisitor> {

  private DID publicKey;
}
