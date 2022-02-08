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

import kotlin.reflect.KProperty1

// isEqual

/** A constraint that validate if the value is equal to another value. */
data class Equal<V>(val value: V) : Constraint

/** A constraint that validate if the value is equal to another property within the same object. */
data class EqualWith(val property: String) : Constraint

/** Validates if the property value is equal to another value. */
fun <T, V> ValidationBuilder<T, V>.isEqual(value: V) =
    validate(Equal(value)) { v, _ -> v == value }

/** Validates if the property value is equal to another property within the same object. */
fun <T, V> ValidationBuilder<T, V>.isEqual(property: KProperty1<T, V?>) =
    validate(EqualWith(property.name)) { v, bean -> property(bean)?.let { it == v } ?: true }


// isIn

/** A constraint that validate if the value is equal to one of the values. */
data class In<V : Any>(val allowedValues: Collection<V>) : Constraint

/** Validates if the property value is equal to one of the values. */
fun <T, V : Any> ValidationBuilder<T, V>.isIn(allowedValues: Collection<V>) =
    validate(In(allowedValues)) { v, _ -> v in allowedValues }

/** Validates if the property value is equal to one of the values. */
fun <T, V : Any> ValidationBuilder<T, V>.isIn(vararg allowedValues: V) = isIn(allowedValues.toList())


// isNotIn

/** A constraint that validate if the value is not equal to one of the values. */
data class NotIn<V : Any>(val forbiddenValues: Collection<V>) : Constraint

/** Validates if the property value is not equal to one of the values. */
fun <T, V : Any> ValidationBuilder<T, V>.isNotIn(forbiddenValues: Collection<V>) =
    validate(NotIn(forbiddenValues)) { v, _ -> v !in forbiddenValues }

/** Validates if the property value is not equal to one of the values. */
fun <T, V : Any> ValidationBuilder<T, V>.isNotIn(vararg forbiddenValues: V) = isNotIn(forbiddenValues.toList())
