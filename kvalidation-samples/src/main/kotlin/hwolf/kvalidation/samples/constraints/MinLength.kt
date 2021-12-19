package hwolf.kvalidation.samples.constraints

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ValidationBuilder
import hwolf.kvalidation.validate

data class MinLength<T>(val min: T) : Constraint

fun <T> ValidationBuilder<T, String>.hasMinLength(min: Int) =
    validate(MinLength(min)) { v, _ ->
        v.length >= min
    }
