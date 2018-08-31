package jp.co.soramitsu.sora.sdk.did.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@EqualsAndHashCode
@JsonInclude(value = NON_NULL)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Proof {

  @NonNull
  @JsonUnwrapped
  private Options options;

  @NonNull
  @JsonProperty("signatureValue")
  private byte[] signatureValue;

}
