package hwolf.kvalidation

// isEmpty

/** Validates if the [String] property is empty. */
fun <T> ValidationBuilder<T, String>.isEmpty() = validate(Empty) { v, _ -> v.isEmpty() }


// isNotEmpty

/** Validates if the [String] property is not empty. */
fun <T> ValidationBuilder<T, String>.isNotEmpty() = validate(NotEmpty) { v, _ -> v.isNotEmpty() }


// isNotBlank

/** A constraint that validate if the value is not blank. */
object NotBlank : Constraint

/** Validates if the [String] property is not blank. */
fun <T> ValidationBuilder<T, String>.isNotBlank() = validate(NotBlank) { v, _ -> v.isNotBlank() }


// hasLength

/** A constraint that validate if the size of [String] value is within the limits (min and max). */
data class Length(val min: Int, val max: Int) : Constraint

/** Validates if the [String] property length is within the limits (min and max). */
fun <T> ValidationBuilder<T, String>.hasLength(min: Int, max: Int) =
    validate(Length(min, max)) { v, _ -> v.length in min..max }
