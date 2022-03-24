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

package io.github.hwolf.kvalidation

/** A constraint that validate if the size of value is within the limits (min and max). */
data class Size(val min: Int, val max: Int) : Constraint

/**  Validates if the [Iterable] property size is within the limits (min and max). */
@JvmName(name = "sizeIterable")
fun <T, V> ValidationBuilder<T, out Iterable<V>>.size(min: Int, max: Int) =
    validate(Size(min, max)) { v, _ -> v.count() in min..max }

/**  Validates if the [Collection] property size is within the limits (min and max). */
@JvmName(name = "sizeCollection")
fun <T, V> ValidationBuilder<T, out Collection<V>>.size(min: Int, max: Int) =
    validate(Size(min, max)) { v, _ -> v.size in min..max }

/**  Validates if the [Array] property size is within the limits (min and max). */
@JvmName(name = "sizeArray")
fun <T, V> ValidationBuilder<T, Array<V>>.size(min: Int, max: Int) =
    validate(Size(min, max)) { v, _ -> v.size in min..max }

/**  Validates if the [Map] property size is within the limits (min and max). */
@JvmName(name = "sizeMap")
fun <T, K, V> ValidationBuilder<T, out Map<K, V>>.size(min: Int, max: Int) =
    validate(Size(min, max)) { v, _ -> v.size in min..max }
