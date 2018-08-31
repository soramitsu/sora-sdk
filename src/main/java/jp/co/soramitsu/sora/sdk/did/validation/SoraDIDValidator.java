package jp.co.soramitsu.sora.sdk.did.validation;

import static java.lang.String.format;
import static java.lang.String.join;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import jp.co.soramitsu.sora.sdk.did.model.dto.DID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.val;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoraDIDValidator implements ConstraintValidator<SoraDIDConstraint, DID> {

  public static final String SORA_METHOD = "sora";
  private SoraDIDTypeEnum type;

  @Override
  public void initialize(SoraDIDConstraint constraintAnnotation) {
    this.type = constraintAnnotation.type();
  }

  @Override
  public boolean isValid(DID value, ConstraintValidatorContext context) {
    return checkMethod(value, context) && checkIdentifiers(value.getIdentifiers(), context);
  }

  public boolean checkMethod(DID did, ConstraintValidatorContext context) {
    if (!did.getMethod().equals(SORA_METHOD)) {
      context
          .buildConstraintViolationWithTemplate(
              format("Method should be equal to '%s', but %s", SORA_METHOD, did.getMethod())
          )
          .addConstraintViolation();

      return false;
    }

    return true;
  }

  public boolean checkIdentifiers(List<String> ids, ConstraintValidatorContext context) {
    switch (type) {
      case ARBITRARY:
        return true;

      case UUID:
        return isValidType(type, ids, context);

      case ANY:
        // valid to any of the above types
        boolean valid = false;
        for (val t : SoraDIDTypeEnum.values()) {
          valid |= isValidType(t, ids, context);
        }
        return valid;

      default:
        return false;
    }
  }

  public boolean isValidType(SoraDIDTypeEnum type, List<String> ids,
      ConstraintValidatorContext context) {
    if (ids.size() != 2) {
      context
          .buildConstraintViolationWithTemplate(
              format("Expected type:id. Got:%s.", join(":", ids))
          )
          .addConstraintViolation();

      return false;
    }

    val idType = ids.get(0);
    val id = ids.get(1);

    // identifier#1 - type; did:sora:TYPE:VALUE
    if (!ids.get(0).equals(type.getType())) {
      context
          .buildConstraintViolationWithTemplate(
              format("Wrong type. Expected %s, got %s.", type.getType(), idType)
          )
          .addConstraintViolation();

      return false;
    }

    // identifier#2 - value
    if (!type.getPattern().matcher(ids.get(1)).matches()) {
      context
          .buildConstraintViolationWithTemplate(
              format("Wrong id. Expected %s to match %s regex.", id, type.getPattern().pattern())
          )
          .addConstraintViolation();

      return false;
    }

    return true;
  }
}
