package hwolf.kvalidation

import strikt.api.Assertion
import strikt.assertions.contains
import strikt.assertions.containsExactly

fun <T> forEach(vararg examples: T, action: (T) -> Unit) = examples.toList().forEach(action)

fun Assertion.Builder<ValidationResult>.isValid() =
    assert("is valid") {
        when {
            it.isValid -> pass()
            else -> fail()
        }
    }

fun Assertion.Builder<ValidationResult>.hasViolations(vararg violations: ConstraintViolation) =
    get { errors }.contains(*violations)

fun Assertion.Builder<ValidationResult>.hasExactlyViolations(vararg violations: ConstraintViolation) =
    get { errors }.containsExactly(*violations)
