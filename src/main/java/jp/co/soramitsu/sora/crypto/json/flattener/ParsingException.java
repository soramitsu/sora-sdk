package jp.co.soramitsu.sora.crypto.json.flattener;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParsingException extends Exception {

  ParsingException(Exception e) {
    super(e);
  }

  ParsingException(String cause) {
    super(cause);
  }
}
