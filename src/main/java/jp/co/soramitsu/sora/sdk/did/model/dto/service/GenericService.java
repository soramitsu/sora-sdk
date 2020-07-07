package jp.co.soramitsu.sora.sdk.did.model.dto.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URL;
import jp.co.soramitsu.sora.sdk.did.model.dto.Service;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class GenericService extends Service {

  @JsonCreator
  public GenericService(
      @JsonProperty("type") String type,
      @JsonProperty("serviceEndpoint") URL ... endpointURL) {
    super(type, endpointURL);
  }
}
