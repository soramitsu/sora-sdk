package jp.co.soramitsu.sora.crypto.proof;

import jp.co.soramitsu.sora.crypto.common.SignatureTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Options {

  @NonNull
  private SignatureTypeEnum type;

  /**
   * ISO8601 time.
   */
  @NonNull
  private String created;

  @NonNull
  private String creator;

  @NonNull
  private String nonce;

  private String purpose;
}
