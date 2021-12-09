package hwolf.validation.constraints

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder

// isEmpty

fun ConstraintBuilder<String>.isEmpty() = validate(Empty) { it.isEmpty() }


// isNotEmpty

fun ConstraintBuilder<String>.isNotEmpty() = validate(NotEmpty) { it.isNotEmpty() }


// isNotBlank

object NotBlank : Constraint

fun ConstraintBuilder<String>.isNotBlank() = validate(NotBlank) { it.isNotBlank() }


// hasLength

data class Length(val min: Int, val max: Int) : Constraint

fun ConstraintBuilder<String>.hasLength(min: Int, max: Int) =
    validate(Length(min, max)) { it.length in min..max }


// hasSize

fun ConstraintBuilder<String>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { it.length in min..max }
