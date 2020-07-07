package jp.co.soramitsu.sora.sdk.did.model.dto;

import static java.util.Arrays.stream;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import jp.co.soramitsu.sora.sdk.did.model.Executable;
import jp.co.soramitsu.sora.sdk.did.model.dto.service.GenericService;
import jp.co.soramitsu.sora.sdk.did.model.dto.service.ServiceExecutor;
import lombok.Data;

@JsonDeserialize(as = GenericService.class)
@Data
public abstract class Service implements Executable<ServiceExecutor> {

  @NotNull
  private String type;

  @NotEmpty
  private List<URL> serviceEndpoint;

  private Map<String, Object> details;

  public Service(String type, @NotEmpty URL ...serviceEndpoint) {
    this.serviceEndpoint = new ArrayList<>(Arrays.asList(serviceEndpoint));
    this.type = type;
  }

  public String getType() {
    if (type == null) {
      type = getClass().getCanonicalName();
    }
    return type;
  }

  @Override
  public final void execute(ServiceExecutor serviceExecutor) {
    stream(serviceExecutor.getClass().getDeclaredMethods()).forEach(method -> {
      if (stream(method.getParameterTypes()).anyMatch(aClass -> aClass.equals(this.getClass()))) {
        serviceExecutor.execute(this);
      }
    });
  }
}
