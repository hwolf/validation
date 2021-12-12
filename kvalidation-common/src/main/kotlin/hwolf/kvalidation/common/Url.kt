package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ConstraintBuilder
import org.apache.commons.validator.routines.UrlValidator

object Url : Constraint

fun ConstraintBuilder<String>.isUrl() = validate(Url) {
    UrlValidator.getInstance().isValid(it)
}
