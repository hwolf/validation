package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ConstraintBuilder
import org.apache.commons.validator.routines.UrlValidator

/** A constraint that validate if the value is a URL. */
object Url : Constraint

/** Validates if the property value is a URL. */
fun ConstraintBuilder<String>.isUrl() = validate(Url) {
    UrlValidator.getInstance().isValid(it)
}
