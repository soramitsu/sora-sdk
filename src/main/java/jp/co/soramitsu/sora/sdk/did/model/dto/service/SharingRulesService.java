package jp.co.soramitsu.sora.sdk.did.model.dto.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URL;
import jp.co.soramitsu.sora.sdk.did.model.dto.DID;

public class SharingRulesService extends GenericService {

  @JsonCreator
  public SharingRulesService(
      @JsonProperty("id") DID serviceId,
      @JsonProperty("serviceEndpoint") URL endpointURL
  ) {
    super(serviceId, endpointURL);
  }
}
