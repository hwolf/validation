package hwolf.validation.constraints

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder
import hwolf.validation.ExtConstraintBuilder
import kotlin.reflect.KProperty1

// isEqual

/** A constraint that validate if the value is equal to another value. */
data class Equal<V>(val value: V) : Constraint

/** A constraint that validate if the value is equal to another property within the same object. */
data class EqualWith(val property: String) : Constraint

/** Validates if the property value is equal to another value. */
fun <V> ConstraintBuilder<V>.isEqual(value: V) =
    validate(Equal(value)) { v -> v == value }

/** Validates if the property value is equal to another property within the same object. */
fun <T, V> ExtConstraintBuilder<T, V>.isEqual(property: KProperty1<T, V?>) =
    validate(EqualWith(property.name)) { v, bean -> property(bean)?.let { it == v } ?: true }


// isIn

/** A constraint that validate if the value is equal to one of the values. */
data class In<V : Any>(val allowedValues: Collection<V>) : Constraint

/** Validates if the property value is equal to one of the values. */
fun <V : Any> ConstraintBuilder<V>.isIn(allowedValues: Collection<V>) =
    validate(In(allowedValues)) { it in allowedValues }

/** Validates if the property value is equal to one of the values. */
fun <V : Any> ConstraintBuilder<V>.isIn(vararg allowedValues: V) = isIn(allowedValues.toList())


// isNotIn

/** A constraint that validate if the value is not equal to one of the values. */
data class NotIn<V : Any>(val forbiddenValues: Collection<V>) : Constraint

/** Validates if the property value is not equal to one of the values. */
fun <V : Any> ConstraintBuilder<V>.isNotIn(forbiddenValues: Collection<V>) =
    validate(NotIn(forbiddenValues)) { it !in forbiddenValues }

/** Validates if the property value is not equal to one of the values. */
fun <V : Any> ConstraintBuilder<V>.isNotIn(vararg forbiddenValues: V) = isNotIn(forbiddenValues.toList())
