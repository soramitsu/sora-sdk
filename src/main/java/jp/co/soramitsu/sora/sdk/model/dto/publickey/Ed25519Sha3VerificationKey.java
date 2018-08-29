package jp.co.soramitsu.sora.sdk.model.dto.publickey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jp.co.soramitsu.sora.sdk.model.dto.DID;
import jp.co.soramitsu.sora.sdk.model.dto.PublicKey;
import jp.co.soramitsu.sora.sdk.model.serializer.HexValueCombinedSerializer.HexValueDeserializer;
import jp.co.soramitsu.sora.sdk.model.serializer.HexValueCombinedSerializer.HexValueSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class Ed25519Sha3VerificationKey extends PublicKey {

  private DID owner;

  @JsonSerialize(using = HexValueSerializer.class)
  @JsonDeserialize(using = HexValueDeserializer.class)
  @NonNull
  private byte[] publicKey;

  public Ed25519Sha3VerificationKey(
      DID id,
      byte[] publicKey
  ) {
    this(id, null, publicKey);
  }

  @JsonCreator
  public Ed25519Sha3VerificationKey(
      @JsonProperty("id") DID id,
      @JsonProperty("owner") DID owner,
      @JsonProperty("publicKey") byte[] publicKey
  ) {
    super(id);
    this.owner = owner;
    this.publicKey = publicKey;
  }

  @Override
  public void visit(PublicKeyVisitor visitor) {
    visitor.visit(this);
  }
}

