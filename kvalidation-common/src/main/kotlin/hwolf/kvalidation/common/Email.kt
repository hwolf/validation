package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ConstraintBuilder
import org.apache.commons.validator.routines.EmailValidator

object Email : Constraint

fun ConstraintBuilder<String>.isEmail() = validate(Email) {
    EmailValidator.getInstance().isValid(it)
}
