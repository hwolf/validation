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

/**
 * Represents a type which validates an object and collects the found
 * constraint violations in an instance [ValidationContext].
 */
typealias Validator<T> = (value: T, context: ValidationContext<T>) -> Unit

/**
 * Validate the object [value] with the receiver [Validator] and returns a [ValidationResult].
 */
fun <T> Validator<T>.validate(value: T): ValidationResult {
    val context = ValidationContext(value)
    invoke(value, context)
    return ValidationResult(context.errors)
}
