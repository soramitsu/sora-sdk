package jp.co.soramitsu.sora.json;

import com.fasterxml.jackson.databind.JsonNode;

public class BadJsonException extends RuntimeException {

  public BadJsonException(JsonNode node) {
    super("Bad json: " + node);
  }

  public BadJsonException(Exception e) {
    super(e);
  }

}
