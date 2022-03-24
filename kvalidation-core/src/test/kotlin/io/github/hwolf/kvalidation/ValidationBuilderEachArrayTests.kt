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

class ValidationBuilderEachArrayTests : FunSpec({

    class TestBean(val array: Array<String>)

    val validator = validator<TestBean> {
        TestBean::array each {
            hasValue("1", "3")
        }
    }

    test("An empty array is valid") {
        expectThat(validator.validate(TestBean(array = emptyArray()))).isValid()
    }

    test("One value in the array is invalid") {
        expectThat(validator.validate(TestBean(array = arrayOf("1", "2", "3"))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyPath = listOf(PropertyName("array", 1)),
                    propertyType = PropertyType("String"),
                    propertyValue = "2",
                    constraint = HasValue(allowedValues = listOf("1", "3"))
                ))
    }
})
