package jp.co.soramitsu.sora.sdk.did.model.dto.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URL;
import jp.co.soramitsu.sora.sdk.did.model.dto.DID;
import jp.co.soramitsu.sora.sdk.did.model.dto.Service;

public class GenericService extends Service {

  @JsonCreator
  public GenericService(
      @JsonProperty("id") DID serviceId,
      @JsonProperty("serviceEndpoint") URL endpointURL) {
    super(serviceId, endpointURL);
  }
}
