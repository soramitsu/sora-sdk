package jp.co.soramitsu.sora.crypto.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jp.co.soramitsu.jackson.OneCodeMapper;
import jp.co.soramitsu.sora.crypto.service.JSONCanonizer;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class JSONCanonizerWithOneCoding implements JSONCanonizer {

  private final ObjectMapper mapper = new OneCodeMapper();

  @Override
  public byte[] canonize(Object obj) throws IOException {
    return mapper.writeValueAsBytes(obj);
  }
}
