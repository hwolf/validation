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
import io.kotest.datatest.withData
import strikt.api.expectThat

class LessOrEqualWithTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isLessOrEqual(TestBean::prop2) } }

    context("is less or equal than") {
        withData(TestBean(20, prop2 = 21), TestBean(23, prop2 = 23), TestBean(20, prop2 = null)) { bean ->
            val actual = validator.validate(bean)
            expectThat(actual).isValid()
        }
    }
    context("is not less or equal than") {
        withData(TestBean(143, prop2 = 22), TestBean(23, prop2 = 22)) { bean ->
            val actual = validator.validate(bean)
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = bean.prop1,
                constraint = LessOrEqualWith("prop2")))
        }
    }
})
