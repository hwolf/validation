package hwolf.kvalidation

/**
 * Represents a type which validates an object and collects the found
 * constraint violations in an instance [ValidationContext].
 */
typealias Validator<T> = (value: T, context: ValidationContext<T>) -> Unit

/**
 * Validate the object [value] with the receiver [Validator] and returns a [ValidationResult].
 */
fun <T> Validator<T>.validator(value: T): ValidationResult {
    val context = ValidationContext(value)
    invoke(value, context)
    return ValidationResult(context.errors)
}
