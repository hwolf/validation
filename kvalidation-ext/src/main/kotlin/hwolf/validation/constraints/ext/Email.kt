package hwolf.validation.constraints.ext

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder
import org.apache.commons.validator.routines.EmailValidator

object Email : Constraint

fun ConstraintBuilder<String>.isEmail() = validate(Email) {
    EmailValidator.getInstance().isValid(it)
}
