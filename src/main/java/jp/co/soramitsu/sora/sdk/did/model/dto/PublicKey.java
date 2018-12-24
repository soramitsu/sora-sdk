package jp.co.soramitsu.sora.sdk.did.model.dto;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jp.co.soramitsu.sora.sdk.did.model.Executable;
import jp.co.soramitsu.sora.sdk.did.model.dto.publickey.Ed25519Sha3VerificationKey;
import jp.co.soramitsu.sora.sdk.did.model.dto.publickey.PublicKeyExecutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@JsonTypeInfo(use = NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Ed25519Sha3VerificationKey.class, name = "Ed25519Sha3VerificationKey")
})
@AllArgsConstructor
@Data
public abstract class PublicKey implements Executable<PublicKeyExecutor> {

  @NonNull
  private DID id;

  private DID owner;

  @JsonIgnore
  public abstract byte[] getPublicKey();
}
