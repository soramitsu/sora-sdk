package jp.co.soramitsu.sora.sdk.did;

import lombok.val;

public class Utils {

  public static String join(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
    StringBuilder buf = new StringBuilder();
    val iter = elements.iterator();
    while (iter.hasNext()) {
      buf.append(iter.next());

      if (iter.hasNext()) {
        buf.append(delimiter);
      } else {
        break;
      }
    }

    return buf.toString();
  }
}
