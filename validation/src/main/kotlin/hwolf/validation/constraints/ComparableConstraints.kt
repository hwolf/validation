package hwolf.validation.constraints

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder
import hwolf.validation.ExtConstraintBuilder
import kotlin.reflect.KProperty1

// isLessThan

data class Less<V>(val value: V) : Constraint
data class LessWith(val property: String) : Constraint

fun <V : Comparable<V>> ConstraintBuilder<V>.isLess(value: V) =
    validate(Less(value)) { it < value }

fun <T, V : Comparable<V>> ExtConstraintBuilder<T, out V>.isLess(property: KProperty1<T, V?>) =
    validate(LessWith(property.name)) { v, bean -> property(bean)?.let { v < it } ?: true }


// isLessOrEqualThan

data class LessOrEqual<V>(val value: V) : Constraint
data class LessOrEqualWith(val property: String) : Constraint

fun <V : Comparable<V>> ConstraintBuilder<V>.isLessOrEqual(value: V) =
    validate(LessOrEqual(value)) { it <= value }

fun <T, V : Comparable<V>> ExtConstraintBuilder<T, out V>.isLessOrEqual(property: KProperty1<T, V?>) =
    validate(LessOrEqualWith(property.name)) { v, bean -> property(bean)?.let { v <= it } ?: true }


// isGreaterThan

data class Greater<V>(val value: V) : Constraint
data class GreaterWith(val property: String) : Constraint

fun <V : Comparable<V>> ConstraintBuilder<V>.isGreater(value: V) =
    validate(Greater(value)) { it > value }

fun <T, V : Comparable<V>> ExtConstraintBuilder<T, out V>.isGreater(property: KProperty1<T, V?>) =
    validate(GreaterWith(property.name)) { v, bean -> property(bean)?.let { v > it } ?: true }


// isGreaterOrEqualThan

data class GreaterOrEqual<V>(val value: V) : Constraint
data class GreaterOrEqualWith(val property: String) : Constraint

fun <V : Comparable<V>> ConstraintBuilder<V>.isGreaterOrEqual(value: V) =
    validate(GreaterOrEqual(value)) { it >= value }

fun <T, V : Comparable<V>> ExtConstraintBuilder<T, out V>.isGreaterOrEqual(property: KProperty1<T, V?>) =
    validate(GreaterOrEqualWith(property.name)) { v, bean -> property(bean)?.let { v >= it } ?: true }


// isBetween

data class Between<V>(val start: V, val end: V) : Constraint

fun <V : Comparable<V>> ConstraintBuilder<V>.isBetween(start: V, end: V) =
    validate(Between(start, end)) { it in start.rangeTo(end) }


// isNotBetween

data class NotBetween<V>(val start: V, val end: V) : Constraint

fun <V : Comparable<V>> ConstraintBuilder<V>.isNotBetween(start: V, end: V) =
    validate(NotBetween(start, end)) { it !in start.rangeTo(end) }
