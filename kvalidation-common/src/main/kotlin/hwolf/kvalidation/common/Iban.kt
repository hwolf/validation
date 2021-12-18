package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ValidationBuilder
import hwolf.kvalidation.validate
import org.apache.commons.validator.routines.IBANValidator

/** A constraint that validate if the value is an IBAN. */
object Iban : Constraint

/** Validates if the property value is an IBAN. */
fun <T> ValidationBuilder<T, String>.isIban() = validate(Iban) { v, _ ->
    IBANValidator.getInstance().isValid(v)
}
