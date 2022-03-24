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

/** A constraint that validate if the value is equal to one of the values. */
data class HasValue<V : Any>(val allowedValues: Collection<V>) : Constraint

/** Validates if the property value is equal to one of the values. */
fun <T, V : Any> ValidationBuilder<T, V>.hasValue(allowedValues: Collection<V>) =
    validate(HasValue(allowedValues)) { v, _ -> v in allowedValues }

/** Validates if the property value is equal to one of the values. */
fun <T, V : Any> ValidationBuilder<T, V>.hasValue(vararg allowedValues: V) = hasValue(allowedValues.toList())
