package hwolf.kvalidation

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KType

class ValidationContext<T> private constructor(
    val bean: T,
    private val errs: MutableList<ConstraintViolation>,
    private val propertyPath: List<PropertyName>,
    private val propertyType: PropertyType?
) {
    constructor(bean: T) : this(bean, mutableListOf(), emptyList(), null)

    val errors get() = errs.toList()

    internal fun constraintViolation(constraint: Constraint, propertyValue: Any?) {
        constraintViolation(constraint.messageKey, constraint, propertyValue)
    }

    fun constraintViolation(violationId: String, constraint: Constraint, propertyValue: Any?) {
        errs.add(ConstraintViolation(
            violationId = violationId,
            propertyPath = propertyPath,
            propertyType = propertyType,
            propertyValue = propertyValue,
            constraint = constraint
        ))
    }

    fun <T, V> withProperty(property: KProperty1<T, V>, value: T) = ValidationContext(
        bean = value,
        errs = errs,
        propertyPath = buildPropertyName(property),
        propertyType = buildTypeName(findClass(property.returnType)))

    fun <T, V> withProperty(property: KProperty1<T, V>, collection: T, key: Any, value: Any?) =
        withProperty(property, collection, key, when (value) {
            null -> null
            else -> value::class
        })

    private fun <T, V> withProperty(property: KProperty1<T, V>, value: T, key: Any, valueClass: KClass<*>?) =
        ValidationContext(
            bean = value,
            errs = errs,
            propertyPath = buildPropertyName(property, key),
            propertyType = buildTypeName(valueClass))

    fun <V> withRoot(root: V): ValidationContext<V> = ValidationContext(
        bean = root,
        errs = errs,
        propertyPath = propertyPath,
        propertyType = propertyType)

    private fun buildPropertyName(property: KProperty1<*, *>, key: Any? = null) =
        propertyPath + PropertyName(property.name, key)

    private fun buildTypeName(klass: KClass<*>?) = klass?.let { t -> t.simpleName?.let { n -> PropertyType(n) } }

    private fun findClass(type: KType) = type.classifier as? KClass<*>
}

