package hwolf.validation.constraints

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder

// isEmpty

/** Validates if the [String] property is empty. */
fun ConstraintBuilder<String>.isEmpty() = validate(Empty) { it.isEmpty() }


// isNotEmpty

/** Validates if the [String] property is not empty. */
fun ConstraintBuilder<String>.isNotEmpty() = validate(NotEmpty) { it.isNotEmpty() }


// isNotBlank

/** A constraint that validate if the value is not blank. */
object NotBlank : Constraint

/** Validates if the [String] property is not blank. */
fun ConstraintBuilder<String>.isNotBlank() = validate(NotBlank) { it.isNotBlank() }


// hasLength

/** A constraint that validate if the size of [String] value is within the limits (min and max). */
data class Length(val min: Int, val max: Int) : Constraint

/** Validates if the [String] property length is within the limits (min and max). */
fun ConstraintBuilder<String>.hasLength(min: Int, max: Int) =
    validate(Length(min, max)) { it.length in min..max }
