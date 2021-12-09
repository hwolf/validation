package hwolf.validation.constraints

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder
import hwolf.validation.ExtConstraintBuilder
import kotlin.reflect.KProperty1

// isEqual

data class Equal<V>(val value: V) : Constraint
data class EqualWith(val property: String) : Constraint

fun <V> ConstraintBuilder<V>.isEqual(value: V) =
    validate(Equal(value)) { v -> v == value }

fun <T, V> ExtConstraintBuilder<T, V>.isEqual(property: KProperty1<T, V?>) =
    validate(EqualWith(property.name)) { v, bean -> property(bean)?.let { it == v } ?: true }


// isIn

data class In<V : Any>(val allowedValues: Collection<V>) : Constraint

fun <V : Any> ConstraintBuilder<V>.isIn(allowedValues: Collection<V>) =
    validate(In(allowedValues)) { it in allowedValues }

fun <V : Any> ConstraintBuilder<V>.isIn(vararg allowedValues: V) = isIn(allowedValues.toList())


// isNotIn

data class NotIn<V : Any>(val forbiddenValues: Collection<V>) : Constraint

fun <V : Any> ConstraintBuilder<V>.isNotIn(forbiddenValues: Collection<V>) =
    validate(NotIn(forbiddenValues)) { it !in forbiddenValues }

fun <V : Any> ConstraintBuilder<V>.isNotIn(vararg forbiddenValues: V) = isNotIn(forbiddenValues.toList())
