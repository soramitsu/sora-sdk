package jp.co.soramitsu.sora.sdk.did.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.ParseException;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Retention(value = RUNTIME)
@Target(value = FIELD)
@Constraint(validatedBy = ISO8601NormalizedTime.Vaidatior.class)
public @interface ISO8601NormalizedTime {

  class Vaidatior implements ConstraintValidator<ISO8601NormalizedTime, String> {

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
