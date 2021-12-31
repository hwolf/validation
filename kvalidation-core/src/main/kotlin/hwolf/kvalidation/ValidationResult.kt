package hwolf.kvalidation

/**
 * The result of a validation.
 *
 * @property violations List of [ConstraintViolation] found during validation
 * @property isValid `true` when no [ConstraintViolation] was found, otherwise `false`
 */
data class ValidationResult(
    val violations: List<ConstraintViolation>
) {
    val isValid = violations.isEmpty()
}
