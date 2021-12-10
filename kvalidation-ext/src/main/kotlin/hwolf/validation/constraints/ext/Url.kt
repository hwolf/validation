package hwolf.validation.constraints.ext

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder
import org.apache.commons.validator.routines.UrlValidator

object Url : Constraint

fun ConstraintBuilder<String>.isUrl() = validate(Url) {
    UrlValidator.getInstance().isValid(it)
}
