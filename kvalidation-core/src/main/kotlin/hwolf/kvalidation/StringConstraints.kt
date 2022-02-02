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
package hwolf.kvalidation

// isEmpty

/** Validates if the [String] property is empty. */
fun <T> ValidationBuilder<T, String>.isEmpty() = validate(Empty) { v, _ -> v.isEmpty() }


// isNotEmpty

/** Validates if the [String] property is not empty. */
fun <T> ValidationBuilder<T, String>.isNotEmpty() = validate(NotEmpty) { v, _ -> v.isNotEmpty() }


// isNotBlank

/** A constraint that validate if the value is not blank. */
object NotBlank : Constraint

/** Validates if the [String] property is not blank. */
fun <T> ValidationBuilder<T, String>.isNotBlank() = validate(NotBlank) { v, _ -> v.isNotBlank() }


// hasLength

/** A constraint that validate if the size of [String] value is within the limits (min and max). */
data class Length(val min: Int, val max: Int) : Constraint

/** Validates if the [String] property length is within the limits (min and max). */
fun <T> ValidationBuilder<T, String>.hasLength(min: Int, max: Int) =
    validate(Length(min, max)) { v, _ -> v.length in min..max }
