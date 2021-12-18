package hwolf.kvalidation

internal typealias PropertyValidator<V, T> = (value: V, context: ValidationContext<T>) -> Unit

internal class ConstraintValidator<V, T>(
    private val constraint: Constraint,
    private val test: (V, T) -> Boolean
) : PropertyValidator<V, T> {

    override fun invoke(value: V, context: ValidationContext<T>) {
        if (!test(value, context.bean)) {
            context.constraintViolation(constraint, value)
        }
    }

    fun updateMessageKey(key: String): ConstraintValidator<V, T> =
        ConstraintValidator(DefaultConstraint(key, constraint.parameters), test)
}
