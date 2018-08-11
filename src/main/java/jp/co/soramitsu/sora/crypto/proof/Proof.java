package jp.co.soramitsu.sora.crypto.proof;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.validation.constraints.NotBlank;
import jp.co.soramitsu.sora.crypto.common.SignatureTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class Proof extends Options {

  @NonNull
  @JsonSerialize(using = HexValueSerializer.class)
  @JsonDeserialize(using = HexValueDeserializer.class)
  private byte[] signatureValue;

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
      @NotBlank @JsonProperty("created") String created,
      @NotBlank @JsonProperty("creator") String creator,
      @NotBlank @JsonProperty("nonce") String nonce,
      @JsonProperty("purpose") String purpose,
      @NonNull @JsonProperty("signatureValue") byte[] signatureValue
  ) {
    super(type, created, creator, nonce, purpose);
    this.signatureValue = signatureValue;
  }
}
