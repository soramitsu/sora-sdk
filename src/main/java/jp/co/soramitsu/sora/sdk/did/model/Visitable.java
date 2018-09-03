package jp.co.soramitsu.sora.sdk.did.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Visitable<T> {

  @JsonIgnore
  void accept(T t);
}