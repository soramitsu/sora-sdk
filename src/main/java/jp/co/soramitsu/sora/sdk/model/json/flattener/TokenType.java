package jp.co.soramitsu.sora.crypto.json.flattener;

import java.util.regex.Pattern;
import lombok.Getter;

@Getter
enum TokenType {
  ARRAY_DELIMITER(Flattener.ARRAY_DELIMITER),
  COLON(":"),
  DICT_DELIMITER(Flattener.DICT_DELIMITER),
  NUMBER("(0)|([1-9][0-9]*)");

  private Pattern pattern;

  TokenType(String s) {
    this.pattern = Pattern.compile(s);
  }
}
