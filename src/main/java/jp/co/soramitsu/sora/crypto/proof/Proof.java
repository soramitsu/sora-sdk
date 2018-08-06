package jp.co.soramitsu.sora.crypto.proof;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jp.co.soramitsu.sora.crypto.common.SignatureTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class Proof extends Options {

  public Proof(Options options, byte[] signatureValue) {
    super(
        options.getType(),
        options.getCreated(),
        options.getCreator(),
        options.getNonce(),
        options.getPurpose()
    );
    this.signatureValue = signatureValue;
  }

  @JsonCreator
  public Proof(
      @NonNull @JsonProperty("type") SignatureTypeEnum type,
      @NonNull @JsonProperty("created") String created,
      @NonNull @JsonProperty("creator") String creator,
      @NonNull @JsonProperty("nonce") String nonce,
      @JsonProperty("purpose") String purpose,
      @NonNull @JsonProperty("signatureValue") byte[] signatureValue
  ) {
    super(type, created, creator, nonce, purpose);
    this.signatureValue = signatureValue;
  }

  @NonNull
  @JsonSerialize(using = HexValueSerializer.class)
  @JsonDeserialize(using = HexValueDeserializer.class)
  private byte[] signatureValue;
}
