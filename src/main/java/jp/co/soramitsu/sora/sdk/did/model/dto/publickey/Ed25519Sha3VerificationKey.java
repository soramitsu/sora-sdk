package jp.co.soramitsu.sora.sdk.did.model.dto.publickey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jp.co.soramitsu.sora.sdk.did.model.dto.DID;
import jp.co.soramitsu.sora.sdk.did.model.dto.PublicKey;
import jp.co.soramitsu.sora.sdk.did.model.serializer.HexValueCombinedSerializer.HexValueDeserializer;
import jp.co.soramitsu.sora.sdk.did.model.serializer.HexValueCombinedSerializer.HexValueSerializer;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
public class Ed25519Sha3VerificationKey extends PublicKey {

  @JsonSerialize(using = HexValueSerializer.class)
  @JsonDeserialize(using = HexValueDeserializer.class)
  @NonNull
  private byte[] publicKey;

  public Ed25519Sha3VerificationKey(DID id, byte[] publicKey) {
    this(id, null, publicKey);
  }

  @JsonCreator
  public Ed25519Sha3VerificationKey(
      @JsonProperty("id") DID id,
      @JsonProperty("owner") DID owner,
      @JsonProperty("publicKey") byte[] publicKey) {
    super(id, owner);
    this.publicKey = publicKey;
  }

  @Override
  public void accept(PublicKeyVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public byte[] getPublicKeyValue() {
    return publicKey;
  }
}
