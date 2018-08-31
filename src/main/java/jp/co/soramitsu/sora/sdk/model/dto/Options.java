package jp.co.soramitsu.sora.sdk.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import javax.validation.constraints.NotBlank;
import jp.co.soramitsu.sora.sdk.model.type.SignatureTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Options {

  @NonNull
  @JsonProperty("type")
  private SignatureTypeEnum type;

  @NonNull
  @JsonProperty("created")
  private Instant created;

  @NonNull
  @JsonProperty("creator")
  private DID creator;

  @NotBlank
  @JsonProperty("nonce")
  private String nonce;

  @JsonProperty("purpose")
  private String purpose;

  @JsonCreator
  public Options(
      @NonNull @JsonProperty("type") SignatureTypeEnum type,
      @NonNull @JsonProperty("created") Instant created,
      @NonNull @JsonProperty("creator") DID creator,
      @NonNull @JsonProperty("nonce") String nonce,
      @JsonProperty("purpose") String purpose
  ) {
    this.type = type;
    this.created = created;
    this.creator = creator;
    this.nonce = nonce;
    this.purpose = purpose;
  }
}
