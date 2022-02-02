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

class SizeIterableTests : FunSpec({

    val validator = validator<IterableBean> { IterableBean::range { hasSize(2, 4) } }

    context("has size") {
        withData(nameFn = Any::toString, IntRange(0, 1), IntRange(0, 3)) { value ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).isValid()
        }
    }
    context("has not size") {
        withData(nameFn = Any::toString, IntRange(2, 2), IntRange(0, 4)) { value ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("range")),
                propertyType = PropertyType("Iterable"),
                propertyValue = value,
                constraint = Size(2, 4)))
        }
    }
})
