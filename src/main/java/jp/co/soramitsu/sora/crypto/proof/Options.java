package jp.co.soramitsu.sora.crypto.proof;

import javax.validation.constraints.NotBlank;
import jp.co.soramitsu.sora.crypto.type.SignatureTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Options {

  @NonNull
  private SignatureTypeEnum type;

  /**
   * ISO8601 time.
   */
  @NotBlank
  private String created;

  @NotBlank
  private String creator;

  @NotBlank
  private String nonce;

  private String purpose;
}
