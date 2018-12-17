package jp.co.soramitsu.sora.sdk.did.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.ParseException;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Annotation that adds validation to String fields. String is valid, if it is ISO8601 time
 * normalized to UTC+00.
 */
@Retention(value = RUNTIME)
@Target(value = FIELD)
@Constraint(validatedBy = ISO8601NormalizedDateTime.Vaidatior.class)
public @interface ISO8601NormalizedDateTime {

  class Vaidatior implements ConstraintValidator<ISO8601NormalizedDateTime, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      try {
        ISO8601DateTimeFormatter.parse(value);
        return true;
      } catch (ParseException e) {
        return false;
      }
    }
  }
}
