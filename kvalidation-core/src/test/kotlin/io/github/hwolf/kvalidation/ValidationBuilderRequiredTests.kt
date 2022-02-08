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

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat

class ValidationBuilderRequiredTests: FunSpec({

    class TestBean(val field: String?)

    val validator = validator<TestBean> {
        TestBean::field required {
            isIn("x1", "x2")
        }
    }

    test("Required field is not set") {
        expectThat(validator.validate(TestBean(field = null)))
            .hasExactlyViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("field")),
                propertyType = PropertyType("String"),
                propertyValue = null,
                constraint = Required
            ))
    }

    test("Required field is set but value is invalid") {
        expectThat(validator.validate(TestBean(field = "xx")))
            .hasExactlyViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("field")),
                propertyType = PropertyType("String"),
                propertyValue = "xx",
                constraint = In(allowedValues = listOf("x1", "x2"))
            ))
    }

    test("Required field is set and value is valid") {
        expectThat(validator.validate(TestBean(field = "x2"))).isValid()
    }
})
