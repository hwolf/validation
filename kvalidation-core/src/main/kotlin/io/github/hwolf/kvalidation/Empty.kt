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

/** A constraint that validate if the value is empty. */
object Empty : Constraint

/** Validates if the [String] property is empty. */
fun <T> ValidationBuilder<T, String>.empty() = validate(Empty) { v, _ -> v.isEmpty() }

/** Validates if the [Iterable] property is empty. */
@JvmName(name = "emptyIterable")
fun <T, V> ValidationBuilder<T, out Iterable<V>>.empty() = validate(Empty) { v, _ -> v.none() }

/** Validates if the [Collection] property is empty. */
@JvmName(name = "emptyCollection")
fun <T, V> ValidationBuilder<T, out Collection<V>>.empty() = validate(Empty) { v, _ -> v.isEmpty() }

/** Validates if the [Array] property is empty. */
@JvmName(name = "emptyArray")
fun <T, V> ValidationBuilder<T, Array<V>>.empty() = validate(Empty) { v, _ -> v.isEmpty() }

/** Validates if the [Map] property is empty. */
@JvmName(name = "emptyMap")
fun <T, K, V> ValidationBuilder<T, out Map<K, V>>.empty() = validate(Empty) { v, _ -> v.isEmpty() }
