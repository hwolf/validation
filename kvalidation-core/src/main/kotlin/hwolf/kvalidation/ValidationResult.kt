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
    private val violationMap: Map<String, List<ConstraintViolation>> by lazy {
        violations.groupBy { it.propertyName }
    }

    val isValid = violations.isEmpty()

    operator fun get(property: String): List<ConstraintViolation> =
        violationMap[property] ?: emptyList()
}
