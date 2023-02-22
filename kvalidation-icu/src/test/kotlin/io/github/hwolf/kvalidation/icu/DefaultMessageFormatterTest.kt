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
package io.github.hwolf.kvalidation.icu

import io.github.hwolf.kvalidation.ConstraintViolation
import io.github.hwolf.kvalidation.Equal
import io.github.hwolf.kvalidation.Locale
import io.github.hwolf.kvalidation.PropertyName
import io.github.hwolf.kvalidation.PropertyType
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DefaultMessageFormatterTest : FunSpec({

    test("substitute parameters from constraint") {

        // When
        val actual = DefaultMessageFormatter(
            "X {value} X",
            constraintViolation(),
            Locale.GERMAN)

        // Then
        actual shouldBe "X 1.234 X"
    }

    test("substitute parameters propertyValue and propertyType") {

        // When
        val actual = DefaultMessageFormatter(
            "X {propertyValue}/{propertyType} X",
            constraintViolation(),
            Locale.GERMAN)

        // Then
        actual shouldBe "X xxx/String X"
    }
})

private fun constraintViolation() = ConstraintViolation(
    propertyPath = listOf(PropertyName("test")),
    propertyType = PropertyType("String"),
    propertyValue = "xxx",
    constraint = Equal(1234))
