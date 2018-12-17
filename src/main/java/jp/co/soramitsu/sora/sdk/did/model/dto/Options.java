package jp.co.soramitsu.sora.sdk.did.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import jp.co.soramitsu.sora.sdk.did.model.serializer.TimeCombinedSerializer.TimeISO8601Deserializer;
import jp.co.soramitsu.sora.sdk.did.model.serializer.TimeCombinedSerializer.TimeISO8601Serializer;
import jp.co.soramitsu.sora.sdk.did.model.type.SignatureTypeEnum;
import jp.co.soramitsu.sora.sdk.did.validation.ISO8601DateTimeFormatter;
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
  @JsonSerialize(using = TimeISO8601Serializer.class)
  @JsonDeserialize(using = TimeISO8601Deserializer.class)
  private String created;

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
      @NonNull @JsonProperty("created") String created,
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

  public static class OptionsBuilder {

    private String created;

    public OptionsBuilder created(Instant instant) {
      this.created = ISO8601DateTimeFormatter.format(instant);
      return this;
    }

    public OptionsBuilder created(Date date) {
      this.created = ISO8601DateTimeFormatter.format(date);
      return this;
    }

    public OptionsBuilder created(String string) {
      this.created = ISO8601DateTimeFormatter.format(string);
      return this;
    }
  }
}
