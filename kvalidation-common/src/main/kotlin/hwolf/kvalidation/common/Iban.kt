package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ConstraintBuilder
import org.apache.commons.validator.routines.IBANValidator

object Iban : Constraint

fun ConstraintBuilder<String>.isIban() = validate(Iban) {
    IBANValidator.getInstance().isValid(it)
}
