package hwolf.validation

data class  ValidationResult(
    val errors: List<ConstraintViolation>
) {
    val isValid = errors.isEmpty()
}
