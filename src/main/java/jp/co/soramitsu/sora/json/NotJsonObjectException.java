package jp.co.soramitsu.sora.json;

import com.fasterxml.jackson.databind.JsonNode;

public class NotJsonObjectException extends RuntimeException {

  public NotJsonObjectException(JsonNode root) {
    super("Not an object JSON: " + root.toString());
  }

}
