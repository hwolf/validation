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

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat

class ValidationBuilderEachMapTests : FunSpec({

    class TestBean(val map: Map<String, Int>)

    val validator = validator<TestBean> {
        TestBean::map each {
            isIn(1, 3)
        }
    }

    test("An empty map is valid") {
        expectThat(validator.validate(TestBean(map = emptyMap()))).isValid()
    }

    test("One value in the map is invalid") {
        expectThat(validator.validate(TestBean(map = mapOf("x1" to 1, "x2" to 2, "x3" to 3))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyPath = listOf(PropertyName("map", "x2")),
                    propertyType = PropertyType("Int"),
                    propertyValue = 2,
                    constraint = In(allowedValues = listOf(1, 3))
                ))
    }
})
