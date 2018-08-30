package jp.co.soramitsu.sora.sdk.model.dto;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import java.util.UUID;
import jp.co.soramitsu.sora.sdk.did.parser.DIDVisitor;
import jp.co.soramitsu.sora.sdk.did.parser.generated.ParserException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;


@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class DID {

  @JsonIgnore
  private String method;

  @Singular
  @JsonIgnore
  private List<String> identifiers;

  @JsonIgnore
  private String path;

  @JsonIgnore
  private String fragment;

  @JsonCreator
  public static DID parse(String did) throws ParserException {
    return DIDVisitor.parse(did, "did-reference");
  }

  public static DID randomUUID() {
    String uuid = UUID.randomUUID().toString();

    return new DID(
        "sora",
        asList("uuid", uuid),
        null,
        null
    );
  }

  @JsonValue
  public String toString() {
    String did = format("did:%s:%s", method, join(":", identifiers));

    if (nonNull(path)) {
      did += "/" + path;
    }

    if (nonNull(fragment)) {
      did += "#" + fragment;
    }

    return did;
  }

  /**
   * Returns shallow copy of the DID with modified fragment.
   */
  public DID withFragment(String fragment) {
    return new DID(
        method,
        identifiers,
        path,
        fragment
    );
  }

  /**
   * Returns shallow copy of the DID with modified fragment.
   */
  public DID withPath(String path) {
    return new DID(
        method,
        identifiers,
        path,
        fragment
    );
  }
}
