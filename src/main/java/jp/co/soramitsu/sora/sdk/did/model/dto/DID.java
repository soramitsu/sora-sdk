package jp.co.soramitsu.sora.sdk.did.model.dto;

import static java.util.Arrays.asList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import java.util.UUID;
import jp.co.soramitsu.sora.sdk.did.Utils;
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
  public DID(String did) throws ParserException {
    DID d = DID.parse(did);
    this.method = d.method;
    this.identifiers = d.identifiers;
    this.path = d.path;
    this.fragment = d.fragment;
  }

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
    StringBuilder builder = new StringBuilder();
    builder.append("did:");
    builder.append(method);
    builder.append(":");
    builder.append(Utils.join(":", identifiers));

    if (path != null) {
      builder.append("/");
      builder.append(path);
    }

    if (fragment != null) {
      builder.append("#");
      builder.append(fragment);
    }

    return builder.toString();
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
