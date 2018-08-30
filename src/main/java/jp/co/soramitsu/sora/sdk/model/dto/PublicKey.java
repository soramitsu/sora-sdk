package jp.co.soramitsu.sora.sdk.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jp.co.soramitsu.sora.sdk.model.Visitable;
import jp.co.soramitsu.sora.sdk.model.dto.publickey.Ed25519Sha3VerificationKey;
import jp.co.soramitsu.sora.sdk.model.dto.publickey.PublicKeyVisitor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Ed25519Sha3VerificationKey.class, name = "Ed25519Sha3VerificationKey")
})
@Getter
@AllArgsConstructor
@Data
public abstract class PublicKey implements Visitable<PublicKeyVisitor> {

  @NonNull
  private DID id;
}
