@file:Suppress("TooManyFunctions")

package hwolf.validation.constraints

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder

// isEmpty

/** A constraint that validate if the value is empty. */
object Empty : Constraint

/** Validates if the [Iterable] property is empty. */
@JvmName(name = "isEmptyIterable")
fun <V> ConstraintBuilder<out Iterable<V>>.isEmpty() = validate(Empty) { it.count() == 0 }

/** Validates if the [Collection] property is empty. */
@JvmName(name = "isEmptyCollection")
fun <V> ConstraintBuilder<out Collection<V>>.isEmpty() = validate(Empty) { it.isEmpty() }

/** Validates if the [Array] property is empty. */
@JvmName(name = "isEmptyArray")
fun <V> ConstraintBuilder<Array<V>>.isEmpty() = validate(Empty) { it.isEmpty() }

/** Validates if the [Map] property is empty. */
@JvmName(name = "isEmptyMap")
fun <K, V> ConstraintBuilder<out Map<K, V>>.isEmpty() = validate(Empty) { it.isEmpty() }


// isNotEmpty

/** A constraint that validate if the value is not empty. */
object NotEmpty : Constraint

/** Validates if the [Iterable] property is not empty. */
@JvmName(name = "isNotEmptyIterable")
fun <V> ConstraintBuilder<out Iterable<V>>.isNotEmpty() = validate(NotEmpty) { it.count() > 0 }

/** Validates if the [Collection] property is not empty. */
@JvmName(name = "isNotEmptyCollection")
fun <V> ConstraintBuilder<out Collection<V>>.isNotEmpty() = validate(NotEmpty) { it.isNotEmpty() }

/** Validates if the [Array] property is not empty. */
@JvmName(name = "isNotEmptyArray")
fun <V> ConstraintBuilder<Array<V>>.isNotEmpty() = validate(NotEmpty) { it.isNotEmpty() }

/** Validates if the [Map] property is not empty. */
@JvmName(name = "isNotEmptyMap")
fun <K, V> ConstraintBuilder<out Map<K, V>>.isNotEmpty() = validate(NotEmpty) { it.isNotEmpty() }


// hasSize

/** A constraint that validate if the size of value is within the limits (min and max). */
data class Size(val min: Int, val max: Int) : Constraint

/**  Validates if the [Iterable] property size is within the limits (min and max). */
@JvmName(name = "hasSizeIterable")
fun <V> ConstraintBuilder<out Iterable<V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { it.count() in min..max }

/**  Validates if the [Collection] property size is within the limits (min and max). */
@JvmName(name = "hasSizeCollection")
fun <V> ConstraintBuilder<out Collection<V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { it.size in min..max }

/**  Validates if the [Array] property size is within the limits (min and max). */
@JvmName(name = "hasSizeArray")
fun <V> ConstraintBuilder<Array<V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { it.size in min..max }

/**  Validates if the [Map] property size is within the limits (min and max). */
@JvmName(name = "hasSizeMap")
fun <K, V> ConstraintBuilder<out Map<K, V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { it.size in min..max }
