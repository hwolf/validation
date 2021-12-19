package hwolf.kvalidation.common

import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.ValidationResult
import strikt.api.Assertion
import strikt.assertions.contains

fun <T> forEach(vararg examples: T, action: (T) -> Unit) = examples.toList().forEach(action)

fun Assertion.Builder<ValidationResult>.isValid() =
    assert("is valid") {
        when {
            it.isValid -> pass()
            else -> fail()
        }
    }

fun Assertion.Builder<ValidationResult>.hasViolations(vararg expected: ConstraintViolation) =
    get { violations }.contains(*expected)
