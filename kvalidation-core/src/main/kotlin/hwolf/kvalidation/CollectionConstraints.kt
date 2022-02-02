/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("TooManyFunctions")

package hwolf.kvalidation

// isEmpty

/** A constraint that validate if the value is empty. */
object Empty : Constraint

/** Validates if the [Iterable] property is empty. */
@JvmName(name = "isEmptyIterable")
fun <T, V> ValidationBuilder<T, out Iterable<V>>.isEmpty() = validate(Empty) { v, _ -> v.count() == 0 }

/** Validates if the [Collection] property is empty. */
@JvmName(name = "isEmptyCollection")
fun <T, V> ValidationBuilder<T, out Collection<V>>.isEmpty() = validate(Empty) { v, _ -> v.isEmpty() }

/** Validates if the [Array] property is empty. */
@JvmName(name = "isEmptyArray")
fun <T, V> ValidationBuilder<T, Array<V>>.isEmpty() = validate(Empty) { v, _ -> v.isEmpty() }

/** Validates if the [Map] property is empty. */
@JvmName(name = "isEmptyMap")
fun <T, K, V> ValidationBuilder<T, out Map<K, V>>.isEmpty() = validate(Empty) { v, _ -> v.isEmpty() }


// isNotEmpty

/** A constraint that validate if the value is not empty. */
object NotEmpty : Constraint

/** Validates if the [Iterable] property is not empty. */
@JvmName(name = "isNotEmptyIterable")
fun <T, V> ValidationBuilder<T, out Iterable<V>>.isNotEmpty() = validate(NotEmpty) { v, _ -> v.count() > 0 }

/** Validates if the [Collection] property is not empty. */
@JvmName(name = "isNotEmptyCollection")
fun <T, V> ValidationBuilder<T, out Collection<V>>.isNotEmpty() = validate(NotEmpty) { v, _ -> v.isNotEmpty() }

/** Validates if the [Array] property is not empty. */
@JvmName(name = "isNotEmptyArray")
fun <T, V> ValidationBuilder<T, Array<V>>.isNotEmpty() = validate(NotEmpty) { v, _ -> v.isNotEmpty() }

/** Validates if the [Map] property is not empty. */
@JvmName(name = "isNotEmptyMap")
fun <T, K, V> ValidationBuilder<T, out Map<K, V>>.isNotEmpty() = validate(NotEmpty) { v, _ -> v.isNotEmpty() }


// hasSize

/** A constraint that validate if the size of value is within the limits (min and max). */
data class Size(val min: Int, val max: Int) : Constraint

/**  Validates if the [Iterable] property size is within the limits (min and max). */
@JvmName(name = "hasSizeIterable")
fun <T, V> ValidationBuilder<T, out Iterable<V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { v, _ -> v.count() in min..max }

/**  Validates if the [Collection] property size is within the limits (min and max). */
@JvmName(name = "hasSizeCollection")
fun <T, V> ValidationBuilder<T, out Collection<V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { v, _ -> v.size in min..max }

/**  Validates if the [Array] property size is within the limits (min and max). */
@JvmName(name = "hasSizeArray")
fun <T, V> ValidationBuilder<T, Array<V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { v, _ -> v.size in min..max }

/**  Validates if the [Map] property size is within the limits (min and max). */
@JvmName(name = "hasSizeMap")
fun <T, K, V> ValidationBuilder<T, out Map<K, V>>.hasSize(min: Int, max: Int) =
    validate(Size(min, max)) { v, _ -> v.size in min..max }
