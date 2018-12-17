package jp.co.soramitsu.sora.sdk.did.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601DateTimeFormatter {

  private ISO8601DateTimeFormatter() {
  }

  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

  static {
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  public static Date parse(String value) throws ParseException {
    return sdf.parse(value);
  }

  public static String format(String time) {
    return format(Instant.parse(time));
  }

  public static String format(Instant time) {
    return format(Date.from(time));
  }

  public static String format(Date time) {
    return sdf.format(time);
  }
}
