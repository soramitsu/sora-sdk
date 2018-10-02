package jp.co.soramitsu.sora.sdk.crypto.common;

import static org.spongycastle.util.encoders.Hex.decode;
import static org.spongycastle.util.encoders.Hex.toHexString;

import lombok.NonNull;
import lombok.Value;

@Value
public class Hash {

  @NonNull byte[] data;

  public static Hash fromHex(String hex) {
    return new Hash(decode(hex));
  }

  @Override
  public String toString() {
    return toHexString(data);
  }
}
