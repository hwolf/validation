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
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty
import strikt.assertions.map

class ValidationContextTests : FunSpec({

    class ValidationContextTestBean(val prop1: ValidationContextTestBean? = null, val prop2: String = "")

    test("New instance has no errors") {
        val context = ValidationContext(ValidationContextTestBean())
        expectThat(context.errors).isEmpty()
    }
    test("Add validation error") {
        val context = ValidationContext(ValidationContextTestBean())
        context.constraintViolation(NotEmpty, "value")
        expectThat(context.errors).map { it.constraint }.containsExactly(NotEmpty)
    }
    test("propertyName - Add validation error with property") {
        val context = ValidationContext(ValidationContextTestBean())
        val actual = context.withProperty(ValidationContextTestBean::prop1, ValidationContextTestBean())
        actual.constraintViolation(NotEmpty, "value")
        expectThat(actual.errors).containsExactly(ConstraintViolation(
            propertyPath = listOf(PropertyName("prop1")),
            propertyType = PropertyType("ValidationContextTestBean"),
            propertyValue = "value",
            constraint = NotEmpty))
    }
    test("propertyName - Add validation error with property path") {
        val context = ValidationContext(ValidationContextTestBean())
        val actual = context.withProperty(ValidationContextTestBean::prop1, ValidationContextTestBean())
            .withProperty(ValidationContextTestBean::prop2, ValidationContextTestBean())
        actual.constraintViolation(NotEmpty, "value")
        expectThat(actual.errors).containsExactly(ConstraintViolation(
            propertyPath = listOf(PropertyName("prop1"), PropertyName("prop2")),
            propertyType = PropertyType("String"),
            propertyValue = "value",
            constraint = NotEmpty))
    }

    test("propertyName - Add validation error with property and key") {
        val context = ValidationContext(ValidationContextTestBean())
        val actual = context.withProperty(ValidationContextTestBean::prop2, ValidationContextTestBean(), "key", 12)
        actual.constraintViolation(NotEmpty, "value")
        expectThat(actual.errors).containsExactly(ConstraintViolation(
            propertyPath = listOf(PropertyName("prop2", "key")),
            propertyType = PropertyType("Int"),
            propertyValue = "value",
            constraint = NotEmpty))
    }

    test("propertyName - Pop") {
        val context = ValidationContext(ValidationContextTestBean())
        val actual = context.withProperty(ValidationContextTestBean::prop2, ValidationContextTestBean(), "key", "")
        actual.withProperty(ValidationContextTestBean::prop1, ValidationContextTestBean())
            .constraintViolation(NotBlank, "value")
        actual.constraintViolation(NotEmpty, "value")
        expectThat(actual.errors).map { Pair(it.constraint, it.propertyPath) }
            .containsExactly(
                Pair(NotBlank, listOf(PropertyName("prop2", "key"), PropertyName("prop1"))),
                Pair(NotEmpty, listOf(PropertyName("prop2", "key"))))
    }

    test("propertyType - Add validation error with property") {
        val context = ValidationContext(ValidationContextTestBean())
        val actual = context.withProperty(ValidationContextTestBean::prop1, ValidationContextTestBean())
        actual.constraintViolation(NotEmpty, "value")
        expectThat(actual.errors).map { it.propertyType }
            .containsExactly(PropertyType("ValidationContextTestBean"))
    }

    test("propertyType - Add validation error with property path") {
        val context = ValidationContext(ValidationContextTestBean())
        val actual = context.withProperty(property = ValidationContextTestBean::prop1,
            value = ValidationContextTestBean())
            .withProperty(ValidationContextTestBean::prop2, ValidationContextTestBean())
        actual.constraintViolation(NotEmpty, "value")
        expectThat(actual.errors).map { it.propertyType }
            .containsExactly(PropertyType("String"))
    }

    test("propertyType - Add validation error with property and key") {
        val context = ValidationContext(ValidationContextTestBean())
        val actual = context.withProperty(property = ValidationContextTestBean::prop2,
            collection = ValidationContextTestBean(),
            key = "key",
            value = 45)
        actual.constraintViolation(NotEmpty, "value")
        expectThat(actual.errors).map { it.propertyType }
            .containsExactly(PropertyType("Int"))
    }
})
