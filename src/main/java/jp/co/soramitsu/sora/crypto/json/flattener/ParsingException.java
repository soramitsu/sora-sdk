package jp.co.soramitsu.sora.crypto.json.flattener;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParsingException extends Exception {

  public ParsingException(Exception e) {
    super(e);
  }

  public ParsingException(String cause) {
    super(cause);
  }
}
