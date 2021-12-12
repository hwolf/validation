package hwolf.validation

/**
 * The result of a validation.
 *
 * @property errors List of [ConstraintViolation] found during validation
 * @property isValid `true` when no [ConstraintViolation] was found, otherwise `false`
 */
data class  ValidationResult(
    val errors: List<ConstraintViolation>
) {
    val isValid = errors.isEmpty()
}
