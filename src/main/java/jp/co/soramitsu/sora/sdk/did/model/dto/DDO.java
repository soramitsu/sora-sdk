package jp.co.soramitsu.sora.sdk.did.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import jp.co.soramitsu.sora.sdk.did.model.serializer.TimeCombinedSerializer.TimeISO8601Deserializer;
import jp.co.soramitsu.sora.sdk.did.model.serializer.TimeCombinedSerializer.TimeISO8601Serializer;
import jp.co.soramitsu.sora.sdk.did.validation.ISO8601DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = NON_NULL)
@Builder
@EqualsAndHashCode
public class DDO {

  @NonNull
  @JsonProperty("id")
  private DID id;

  @JsonProperty("publicKey")
  @Singular("publicKey")
  private List<PublicKey> publicKey;

  @JsonProperty("authentication")
  @Singular("authentication")
  private List<Authentication> authentication;

  @JsonProperty("service")
  @Singular("service")
  private List<Service> service;

  @JsonProperty("guardian")
  private DID guardian;

  @JsonProperty("created")
  @JsonSerialize(using = TimeISO8601Serializer.class)
  @JsonDeserialize(using = TimeISO8601Deserializer.class)
  private String created;

  @JsonProperty("updated")
  @JsonSerialize(using = TimeISO8601Serializer.class)
  @JsonDeserialize(using = TimeISO8601Deserializer.class)
  private String updated;

  @JsonProperty("proof")
  private Proof proof;

  public static class DDOBuilder {

    private String created;
    private String updated;

    public DDOBuilder created(Instant instant) {
      this.created = ISO8601DateTimeFormatter.format(instant);
      return this;
    }

    public DDOBuilder created(Date date) {
      this.created = ISO8601DateTimeFormatter.format(date);
      return this;
    }

    public DDOBuilder created(String string) {
      this.created = ISO8601DateTimeFormatter.format(string);
      return this;
    }

    public DDOBuilder updated(Instant instant) {
      this.updated = ISO8601DateTimeFormatter.format(instant);
      return this;
    }

    public DDOBuilder updated(Date date) {
      this.updated = ISO8601DateTimeFormatter.format(date);
      return this;
    }

    public DDOBuilder updated(String string) {
      this.updated = ISO8601DateTimeFormatter.format(string);
      return this;
    }
  }
}
