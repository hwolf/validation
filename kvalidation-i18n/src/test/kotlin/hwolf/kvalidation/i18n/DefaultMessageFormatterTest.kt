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
package hwolf.kvalidation.i18n

import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.Equal
import hwolf.kvalidation.Locale
import hwolf.kvalidation.PropertyName
import hwolf.kvalidation.PropertyType
import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class DefaultMessageFormatterTest : FunSpec({

    test("substitute parameters from constraint") {

        // When
        val actual = DefaultMessageFormatter(
            "X {{constraint.value}} X",
            constraintViolation(),
            Locale.GERMAN)

        // Then
        expectThat(actual).isEqualTo("X 1234 X")
    }

    test("substitute parameters propertyValue and propertyType") {

        // When
        val actual = DefaultMessageFormatter(
            "X {{violation.propertyValue}}/{{violation.propertyType}} X",
            constraintViolation(),
            Locale.GERMAN)

        // Then
        expectThat(actual).isEqualTo("X xxx/String X")
    }
})

private fun constraintViolation() = ConstraintViolation(
    propertyPath = listOf(PropertyName("test")),
    propertyType = PropertyType("String"),
    propertyValue = "xxx",
    constraint = Equal(1234))
