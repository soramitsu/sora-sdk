package jp.co.soramitsu.sora.sdk.did.model.dto;

import java.io.IOException;

public class DateTimeParseException extends IOException {

  private static String format(String date) {
    return String.format("Invalid ISO8601 datetime: '%s'", date);
  }

  public DateTimeParseException(String date) {
    super(format(date));
  }

  public DateTimeParseException(String date, Throwable e) {
    super(format(date), e);
  }

}
