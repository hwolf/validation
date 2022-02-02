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

internal typealias PropertyValidator<V, T> = (value: V, context: ValidationContext<T>) -> Unit

internal class ConstraintValidator<V, T>(
    private val constraint: Constraint,
    private val test: (V, T) -> Boolean
) : PropertyValidator<V, T> {

    override fun invoke(value: V, context: ValidationContext<T>) {
        if (!test(value, context.bean)) {
            context.constraintViolation(constraint, value)
        }
    }
}
