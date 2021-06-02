package jp.co.soramitsu.sora.sdk.did.validation;

import static java.time.ZoneOffset.UTC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import lombok.val;

public class ISO8601DateTimeFormatter {

  private ISO8601DateTimeFormatter() {
  }

  private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

  public static Date parse(String value) throws ParseException {
    return createNewFormatter().parse(value);
  }

  public static String format(String time) {
    return format(Instant.parse(time));
  }

  public static String format(Instant time) {
    return format(Date.from(time));
  }

  public static String format(Date time) {
    return createNewFormatter().format(time);
  }

  private static SimpleDateFormat createNewFormatter() {
    val sdf =new SimpleDateFormat(PATTERN);
    sdf.setTimeZone(TimeZone.getTimeZone(UTC));
    return sdf;
  }
}
