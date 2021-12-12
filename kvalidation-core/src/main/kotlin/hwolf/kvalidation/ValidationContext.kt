package hwolf.kvalidation

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KType

class ValidationContext<T> private constructor(
    val bean: T,
    private val errs: MutableList<ConstraintViolation>,
    private val propertyName: String,
    private val propertyType: String?
) {
    constructor(bean: T) : this(bean, mutableListOf(), "", null)

    val errors get() = errs.toList()

    internal fun constraintViolation(constraint: Constraint, propertyValue: Any?) {
        errs.add(ConstraintViolation(
            propertyName = propertyName,
            propertyType = propertyType,
            propertyValue = propertyValue,
            constraint = constraint
        ))
    }

    internal fun <T, V> withProperty(property: KProperty1<T, V>, value: T) = ValidationContext(
        bean = value,
        errs = errs,
        propertyName = buildPropertyName(property),
        propertyType = buildTypeName(findClass(property.returnType)))

    internal fun <T, V> withProperty(property: KProperty1<T, V>, collection: T, key: Any, value: Any?) =
        withProperty(property, collection, key, when (value) {
            null -> null
            else -> value::class
        })

    internal fun <T, V> withProperty(property: KProperty1<T, V>, value: T, key: Any, valueClass: KClass<*>?) =
        ValidationContext(
            bean = value,
            errs = errs,
            propertyName = buildPropertyName(property, key),
            propertyType = buildTypeName(valueClass))

    internal fun <V> withRoot(root: V): ValidationContext<V> = ValidationContext(
        bean = root,
        errs = errs,
        propertyName = propertyName,
        propertyType = propertyType)


    private fun <T, V> buildPropertyName(property: KProperty1<T, V>) = when {
        propertyName.isEmpty() -> property.name
        else -> "$propertyName.${property.name}"
    }

    private fun buildPropertyName(property: KProperty1<*, *>, key: Any) = when {
        propertyName.isEmpty() -> "${property.name}[$key]"
        else -> "$propertyName.${property.name}[$key]"
    }

    private fun buildTypeName(klass: KClass<*>?) = klass?.simpleName

    private fun findClass(type: KType) = type.classifier as? KClass<*>
}
