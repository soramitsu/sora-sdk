package jp.co.soramitsu.sora.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Visitable<T> {

  @JsonIgnore
  void visit(T t);
}
