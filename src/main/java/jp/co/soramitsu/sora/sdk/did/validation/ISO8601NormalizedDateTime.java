package jp.co.soramitsu.sora.sdk.did.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import jp.co.soramitsu.sora.sdk.did.validation.ISO8601NormalizedDateTime.Validator;

/**
 * Annotation that adds validation to String fields. String is valid, if it is ISO8601 time
 * normalized to UTC+00. A null string considered valid.
 */
@Retention(value = RUNTIME)
@Target(value = FIELD)
@Constraint(validatedBy = Validator.class)
public @interface ISO8601NormalizedDateTime {

  String message() default "DateTime must be valid ISO8601 string";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class Validator implements ConstraintValidator<ISO8601NormalizedDateTime, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if (Objects.nonNull(value)) {
        try {
          ISO8601DateTimeFormatter.parse(value);
        } catch (ParseException ignored) {
          return false;
        }
      }
      return true;
    }
  }
}
