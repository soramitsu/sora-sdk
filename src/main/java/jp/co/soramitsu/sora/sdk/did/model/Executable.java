package jp.co.soramitsu.sora.sdk.did.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Executable<T> {

  @JsonIgnore
  void execute(T t);
}
