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

/** A constraint that validate if the value is not empty. */
object NotEmpty : Constraint

/** Validates if the [String] property is not empty. */
fun <T> ValidationBuilder<T, String>.notEmpty() = validate(NotEmpty) { v, _ -> v.isNotEmpty() }

/** Validates if the [Iterable] property is not empty. */
@JvmName(name = "notEmptyIterable")
fun <T, V> ValidationBuilder<T, out Iterable<V>>.notEmpty() = validate(NotEmpty) { v, _ -> v.count() > 0 }

/** Validates if the [Collection] property is not empty. */
@JvmName(name = "notEmptyCollection")
fun <T, V> ValidationBuilder<T, out Collection<V>>.notEmpty() = validate(NotEmpty) { v, _ -> v.isNotEmpty() }

/** Validates if the [Array] property is not empty. */
@JvmName(name = "notEmptyArray")
fun <T, V> ValidationBuilder<T, Array<V>>.notEmpty() = validate(NotEmpty) { v, _ -> v.isNotEmpty() }

/** Validates if the [Map] property is not empty. */
@JvmName(name = "notEmptyMap")
fun <T, K, V> ValidationBuilder<T, out Map<K, V>>.notEmpty() = validate(NotEmpty) { v, _ -> v.isNotEmpty() }
