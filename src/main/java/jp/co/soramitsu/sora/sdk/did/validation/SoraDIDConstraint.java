package jp.co.soramitsu.sora.sdk.did.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static jp.co.soramitsu.sora.sdk.did.validation.SoraDIDTypeEnum.ANY;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Retention(value = RUNTIME)
@Target(value = FIELD)
@Constraint(validatedBy = SoraDIDValidator.class)
public @interface SoraDIDConstraint {

  /**
   * Set DID type explicitly. ANY is one of {ED, UUID, IROHA}. ARBITRARY is any type.
   */
  SoraDIDTypeEnum type() default ANY;
}
