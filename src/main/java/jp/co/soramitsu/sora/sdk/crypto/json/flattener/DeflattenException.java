package jp.co.soramitsu.sora.sdk.crypto.json.flattener;


import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Thrown during {@link Flattener#deflatten(ObjectNode)} when input JSON contains errors.
 */
public class DeflattenException extends RuntimeException {

  DeflattenException(Exception e) {
    super(e);
  }

  DeflattenException(String msg) {
    super(msg);
  }
}
