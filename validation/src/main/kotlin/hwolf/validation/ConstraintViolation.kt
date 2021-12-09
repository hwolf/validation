package hwolf.validation

data class ConstraintViolation(
    val propertyName: String,
    val propertyType: String?,
    val propertyValue: Any?,
    val constraint: Constraint
)
