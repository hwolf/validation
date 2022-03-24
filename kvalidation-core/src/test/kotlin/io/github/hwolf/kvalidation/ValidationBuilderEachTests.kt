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

class ValidationBuilderEachTests : FunSpec({

    class TestBean(val list: List<String>)

    val validator = validator<TestBean> {
        TestBean::list each {
            hasValue("x1", "x3")
        }
    }

    test("An empty list is valid") {
        expectThat(validator.validate(TestBean(list = emptyList()))).isValid()
    }

    test("One value in the list is invalid") {
        expectThat(validator.validate(TestBean(list = listOf("x1", "x2", "x3"))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyPath = listOf(PropertyName("list", 1)),
                    propertyType = PropertyType("String"),
                    propertyValue = "x2",
                    constraint = HasValue(allowedValues = listOf("x1", "x3"))
                ))
    }
})
