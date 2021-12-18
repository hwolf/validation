package hwolf.kvalidation

import kotlin.reflect.KProperty1

// isLessThan

/** A constraint that validate if the value is less than another value. */
data class Less<V>(val value: V) : Constraint

/** A constraint that validate if the value is less than another property within the same object. */
data class LessWith(val property: String) : Constraint

/** Validates if the [Comparable] property is less than another value. */
fun <T, V : Comparable<V>> ValidationBuilder<T, V>.isLess(value: V) =
    validate(Less(value)) { v, _ -> v < value }

/** Validates if the [Comparable] property is less than property within the same object. */
fun <T, V : Comparable<V>> ValidationBuilder<T, out V>.isLess(property: KProperty1<T, V?>) =
    validate(LessWith(property.name)) { v, bean -> property(bean)?.let { v < it } ?: true }


// isLessOrEqualThan

/** A constraint that validate if the value is less or equal than another value. */
data class LessOrEqual<V>(val value: V) : Constraint

/** A constraint that validate if the value is less or equal than another property within the same object. */
data class LessOrEqualWith(val property: String) : Constraint

/** Validates if the [Comparable] property is less or equal than another value. */
fun <T, V : Comparable<V>> ValidationBuilder<T, V>.isLessOrEqual(value: V) =
    validate(LessOrEqual(value)) { v, _ -> v <= value }

/** A constraint that validate if the value is less or equal than another property within the same object. */
fun <T, V : Comparable<V>> ValidationBuilder<T, out V>.isLessOrEqual(property: KProperty1<T, V?>) =
    validate(LessOrEqualWith(property.name)) { v, bean -> property(bean)?.let { v <= it } ?: true }


// isGreaterThan

/** A constraint that validate if the value is greater than another value. */
data class Greater<V>(val value: V) : Constraint

/** A constraint that validate if the value is greater than another property within the same object. */
data class GreaterWith(val property: String) : Constraint

/** Validates if the [Comparable] property is greater than another value. */
fun <T, V : Comparable<V>> ValidationBuilder<T, V>.isGreater(value: V) =
    validate(Greater(value)) { v, _ -> v > value }

/** A constraint that validate if the value is greater than another property within the same object. */
fun <T, V : Comparable<V>> ValidationBuilder<T, out V>.isGreater(property: KProperty1<T, V?>) =
    validate(GreaterWith(property.name)) { v, bean -> property(bean)?.let { v > it } ?: true }


// isGreaterOrEqualThan

/** A constraint that validate if the value is greater or equal than another value. */
data class GreaterOrEqual<V>(val value: V) : Constraint

/** A constraint that validate if the value is greater or equal than another property within the same object. */
data class GreaterOrEqualWith(val property: String) : Constraint

/** Validates if the [Comparable] property is greater or equal than another value. */
fun <T, V : Comparable<V>> ValidationBuilder<T, V>.isGreaterOrEqual(value: V) =
    validate(GreaterOrEqual(value)) { v, _ -> v >= value }

/** A constraint that validate if the value is greater or equal than another property within the same object. */
fun <T, V : Comparable<V>> ValidationBuilder<T, out V>.isGreaterOrEqual(property: KProperty1<T, V?>) =
    validate(GreaterOrEqualWith(property.name)) { v, bean -> property(bean)?.let { v >= it } ?: true }


// isBetween

/** A constraint that validate if the value is between two values. */
data class Between<V>(val start: V, val end: V) : Constraint

/** Validates if the [Comparable] property is between two values. */
fun <T, V : Comparable<V>> ValidationBuilder<T, V>.isBetween(start: V, end: V) =
    validate(Between(start, end)) { v, _ -> v in start.rangeTo(end) }


// isNotBetween

/** A constraint that validate if the value is not between two values. */
data class NotBetween<V>(val start: V, val end: V) : Constraint

/** Validates if the [Comparable] property is not between two values. */
fun <T, V : Comparable<V>> ValidationBuilder<T, V>.isNotBetween(start: V, end: V) =
    validate(NotBetween(start, end)) { v, _ -> v !in start.rangeTo(end) }
