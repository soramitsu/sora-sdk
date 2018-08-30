package jp.co.soramitsu.sora.sdk.did.validation;

import java.util.regex.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public enum SoraDIDTypeEnum {
  ARBITRARY,  // type can be arbitrary

  ANY, // any of the below
  UUID("uuid", "^[0-9A-F]{8}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$");

  private String type;
  private Pattern pattern;

  SoraDIDTypeEnum(@NonNull String type, @NonNull String pattern) {
    this.type = type;
    this.pattern = Pattern.compile(pattern);
  }
}
