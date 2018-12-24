package jp.co.soramitsu.sora.sdk.did.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Executable<T> {

  @JsonIgnore
  void accept(T t);
}
