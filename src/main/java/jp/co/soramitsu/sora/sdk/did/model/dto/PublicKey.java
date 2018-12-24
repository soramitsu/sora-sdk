package jp.co.soramitsu.sora.sdk.did.model.dto;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.MINIMAL_CLASS;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jp.co.soramitsu.sora.sdk.did.model.Executable;
import jp.co.soramitsu.sora.sdk.did.model.dto.publickey.PublicKeyExecutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@JsonTypeInfo(use = MINIMAL_CLASS, property = "type")
@AllArgsConstructor
@Data
public abstract class PublicKey implements Executable<PublicKeyExecutor> {

  @NonNull
  private DID id;

  private DID owner;

  @JsonIgnore
  public abstract byte[] getPublicKey();
}
