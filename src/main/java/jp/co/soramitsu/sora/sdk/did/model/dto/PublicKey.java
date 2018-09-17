package jp.co.soramitsu.sora.sdk.did.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jp.co.soramitsu.sora.sdk.did.model.Visitable;
import jp.co.soramitsu.sora.sdk.did.model.dto.publickey.Ed25519Sha3VerificationKey;
import jp.co.soramitsu.sora.sdk.did.model.dto.publickey.PublicKeyVisitor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Ed25519Sha3VerificationKey.class, name = "Ed25519Sha3VerificationKey")
})
@AllArgsConstructor
@Data
public abstract class PublicKey implements Visitable<PublicKeyVisitor> {

  @NonNull
  private DID id;

  private DID owner;

  @JsonIgnore
  public abstract byte[] getPublicKey();
}
