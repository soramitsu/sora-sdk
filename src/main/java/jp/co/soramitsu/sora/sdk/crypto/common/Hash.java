package jp.co.soramitsu.sora.sdk.crypto.common;

import lombok.NonNull;
import lombok.Value;
import org.spongycastle.util.encoders.Hex;

@Value
public class Hash {

  @NonNull byte[] data;

  public static Hash fromHex(String hex) {
    return new Hash(Hex.decode(hex));
  }

  @Override
  public String toString() {
    return Hex.toHexString(data);
  }
}
