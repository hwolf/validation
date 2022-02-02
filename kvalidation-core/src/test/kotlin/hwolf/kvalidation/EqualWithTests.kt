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
import io.kotest.datatest.withData
import strikt.api.expectThat

class EqualWithTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isEqual(TestBean::prop2) } }

    context("is equal") {
        withData(Pair(3, 3), Pair(2, null)) { (v1, v2) ->
            val actual = validator.validate(TestBean(prop1 = v1, prop2 = v2))
            expectThat(actual).isValid()
        }
    }
    context("is not equal") {
        withData(Pair(0, 1), Pair(2, 1)) { (v1, v2) ->
            val actual = validator.validate(TestBean(prop1 = v1, prop2 = v2))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = v1,
                constraint = EqualWith("prop2")))
        }
    }
})
