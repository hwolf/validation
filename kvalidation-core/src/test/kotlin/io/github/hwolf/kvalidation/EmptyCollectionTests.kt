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

class EmptyCollectionTests : FunSpec({

    val validator = validator { ListBean::list { empty() } }

    context("is empty") {
        withData(nameFn = Any::toString, listOf(emptyList<String>())) { value ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is not empty") {
        withData(nameFn = Any::toString, listOf("x1", "x2"), listOf("")) { value ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("list")),
                propertyType = PropertyType("List"),
                propertyValue = value,
                constraint = Empty))
        }
    }
})
