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
package io.github.hwolf.kvalidation.common

import io.github.hwolf.kvalidation.ConstraintViolation
import io.github.hwolf.kvalidation.ValidationResult
import strikt.api.Assertion
import strikt.assertions.contains

fun Assertion.Builder<ValidationResult>.isValid() =
    assert("is valid") {
        when {
            it.isValid -> pass()
            else -> fail()
        }
    }

fun Assertion.Builder<ValidationResult>.hasViolations(vararg expected: ConstraintViolation) =
    get { violations }.contains(*expected)
