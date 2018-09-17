package jp.co.soramitsu.sora.sdk.did.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
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
  private Instant created;

  @JsonProperty("updated")
  private Instant updated;

  @JsonProperty("proof")
  private Proof proof;
}
