package jp.co.soramitsu.sora.crypto.json.flattener;


import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Thrown during {@link Flattener#deflatten(ObjectNode)} when input JSON contains errors.
 */
public class DeflattenException extends RuntimeException {

  public DeflattenException(Exception e) {
    super(e);
  }

  public DeflattenException(String msg) {
    super(msg);
  }
}
