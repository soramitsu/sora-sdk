package jp.co.soramitsu.sora.sdk.did.model.dto;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.MINIMAL_CLASS;
import static java.util.Arrays.stream;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.net.URL;
import jp.co.soramitsu.sora.sdk.did.model.Executable;
import jp.co.soramitsu.sora.sdk.did.model.dto.service.ServiceExecutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@JsonTypeInfo(use = MINIMAL_CLASS, property = "type")
@Data
@AllArgsConstructor
public abstract class Service implements Executable<ServiceExecutor> {

  @NonNull
  DID id;

  @NonNull
  URL serviceEndpoint;

  @Override
  public final void accept(ServiceExecutor serviceExecutor) {
    stream(serviceExecutor.getClass().getDeclaredMethods()).forEach(method -> {
      if (stream(method.getParameterTypes()).anyMatch(aClass -> aClass.equals(this.getClass()))) {
        serviceExecutor.execute(this);
      }
    });
  }
}
