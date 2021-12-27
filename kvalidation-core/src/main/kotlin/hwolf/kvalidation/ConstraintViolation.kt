package hwolf.kvalidation

/**
 * Represents a constraint violation
 *
 * @constructor creates a constraint violation
 * @property propertyName the name of the property that violated the constraint
 * @property propertyType the type of the property that violated the constraint
 * @property propertyValue the invalid value
 * @property constraint the violated constraint
 */
data class ConstraintViolation(
    val violationId: String,
    val propertyName: String,
    val propertyType: String?,
    val propertyValue: Any?,
    val constraint: Constraint
) {
    constructor(
        propertyName: String,
        propertyType: String?,
        propertyValue: Any?,
        constraint: Constraint
    ) : this(constraint.messageKey, propertyName, propertyType, propertyValue, constraint)
}

data class PropertyName(
    val name: String,
    val key: Any? = null
) {
    override fun toString() = when (key == null) {
        true -> name
        else -> "$name[$key]"
    }
}

data class PropertyType(
    val name: String
) {
    override fun toString() = name
}
