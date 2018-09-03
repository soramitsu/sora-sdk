package jp.co.soramitsu.sora.sdk.did.model.dto;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.net.URL;
import jp.co.soramitsu.sora.sdk.did.model.Visitable;
import jp.co.soramitsu.sora.sdk.did.model.dto.service.GenericService;
import jp.co.soramitsu.sora.sdk.did.model.dto.service.ServiceVisitor;
import jp.co.soramitsu.sora.sdk.did.model.dto.service.SharingRulesService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = GenericService.class, name = "GenericService"),
    @JsonSubTypes.Type(value = SharingRulesService.class, name = "SharingRulesService")
})
@Getter
@AllArgsConstructor
@Data
public abstract class Service implements Visitable<ServiceVisitor> {

  @NonNull
  DID id;

  @NonNull
  URL serviceEndpoint;
}
