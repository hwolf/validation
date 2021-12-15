package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ConstraintBuilder
import org.apache.commons.validator.routines.IBANValidator

/** A constraint that validate if the value is an IBAN. */
object Iban : Constraint

/** Validates if the property value is an IBAN. */
fun ConstraintBuilder<String>.isIban() = validate(Iban) {
    IBANValidator.getInstance().isValid(it)
}
