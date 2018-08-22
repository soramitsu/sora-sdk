package jp.co.soramitsu.sora.crypto.json.flattener;

import lombok.Value;

@Value
class Token {

  private KeyTypeEnum type;
  private Object value;
}
