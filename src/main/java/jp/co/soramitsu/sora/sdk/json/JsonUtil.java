package jp.co.soramitsu.sora.sdk.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import lombok.experimental.UtilityClass;


@UtilityClass
public class JsonUtil {

  public static ObjectMapper buildMapper() {
    return new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
        .setDefaultPropertyInclusion(Include.NON_NULL);
  }

  public static <T> T deepClone(T obj, Class<T> type) throws IOException {
    byte[] ser = buildMapper().writeValueAsBytes(obj);
    return buildMapper().readValue(ser, type);
  }
}
