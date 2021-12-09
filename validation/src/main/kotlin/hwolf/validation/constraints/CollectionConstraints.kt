@file:Suppress("TooManyFunctions")
package hwolf.validation.constraints

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder

// isEmpty

/**
 * A constraint that validate if the value is empty
 */
object Empty : Constraint

@JvmName(name = "isEmptyIterable")
fun <V> ConstraintBuilder<out Iterable<V>>.isEmpty() = validate(Empty) { it.count() == 0 }

@JvmName(name = "isEmptyCollection")
fun <V> ConstraintBuilder<out Collection<V>>.isEmpty() = validate(Empty) { it.isEmpty() }

@JvmName(name = "isEmptyArray")
fun <V> ConstraintBuilder<Array<V>>.isEmpty() = validate(Empty) { it.isEmpty() }

@JvmName(name = "isEmptyMap")
fun <K, V> ConstraintBuilder<out Map<K, V>>.isEmpty() = validate(Empty) { it.isEmpty() }


// isNotEmpty

/**
 * A constraint that validate if the value is not empty.
 */
object NotEmpty : Constraint

@JvmName(name = "isNotEmptyIterable")
fun <V> ConstraintBuilder<out Iterable<V>>.isNotEmpty() = validate(NotEmpty) { it.count() > 0 }

@JvmName(name = "isNotEmptyCollection")
fun <V> ConstraintBuilder<out Collection<V>>.isNotEmpty() = validate(NotEmpty) { it.isNotEmpty() }

@JvmName(name = "isNotEmptyArray")
fun <V> ConstraintBuilder<Array<V>>.isNotEmpty() = validate(NotEmpty) { it.isNotEmpty() }

@JvmName(name = "isNotEmptyMap")
fun <K, V> ConstraintBuilder<out Map<K, V>>.isNotEmpty() = validate(NotEmpty) { it.isNotEmpty() }


// hasSize

data class Size(val min: Int, val max: Int) : Constraint

@JvmName(name = "hasSizeIterable")
fun <V> ConstraintBuilder<out Iterable<V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { it.count() in min..max }

@JvmName(name = "hasSizeCollection")
fun <V> ConstraintBuilder<out Collection<V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { it.size in min..max }

@JvmName(name = "hasSizeArray")
fun <V> ConstraintBuilder<Array<V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { it.size in min..max }

@JvmName(name = "hasSizeMap")
fun <K, V> ConstraintBuilder<out Map<K, V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { it.size in min..max }
