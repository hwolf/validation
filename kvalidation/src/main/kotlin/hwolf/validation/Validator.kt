package hwolf.validation

typealias Validator<T> = (value: T, context: ValidationContext<T>) -> Unit

fun <T> Validator<T>.validator(value: T): ValidationResult {
    val context = ValidationContext(value)
    invoke(value, context)
    return ValidationResult(context.errors)
}
