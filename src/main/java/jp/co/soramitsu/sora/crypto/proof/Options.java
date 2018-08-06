package jp.co.soramitsu.sora.crypto.proof;

import jp.co.soramitsu.sora.crypto.common.SignatureTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
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
