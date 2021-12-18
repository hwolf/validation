package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ValidationBuilder
import hwolf.kvalidation.validate
import org.apache.commons.validator.routines.UrlValidator

/** A constraint that validate if the value is a URL. */
object Url : Constraint

/** Validates if the property value is a URL. */
fun <T> ValidationBuilder<T, String>.isUrl() = validate(Url) { v, _ ->
    UrlValidator.getInstance().isValid(v)
}
