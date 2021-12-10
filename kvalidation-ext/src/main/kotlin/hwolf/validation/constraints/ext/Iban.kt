package hwolf.validation.constraints.ext

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder
import org.apache.commons.validator.routines.IBANValidator

object Iban : Constraint

fun ConstraintBuilder<String>.isIban() = validate(Iban) {
    IBANValidator.getInstance().isValid(it)
}
