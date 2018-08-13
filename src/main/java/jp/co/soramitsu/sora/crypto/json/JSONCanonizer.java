package jp.co.soramitsu.sora.crypto.json;

import java.io.IOException;

public interface JSONCanonizer {

  /**
   * Canonization - is a term for "stable serialization".
   *
   * @param obj arbitrary POJO or {@link com.fasterxml.jackson.databind.JsonNode}
   * @throws IOException if input argument can not be canonized
   */
  byte[] canonize(final Object obj) throws IOException;
}
