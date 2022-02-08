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
import strikt.assertions.containsExactly

class DefaultMessageCodeResolverTests : FunSpec({

    test("Order of codes for simple property") {

        // When
        val actual = DefaultMessageCodeResolver(ConstraintViolation(
            propertyPath = listOf(PropertyName("test")),
            propertyType = PropertyType("String"),
            propertyValue = "xxx",
            constraint = Equal(1234)))

        // Then
        expectThat(actual).containsExactly(
            "io.github.hwolf.kvalidation.Equal.test.String",
            "io.github.hwolf.kvalidation.Equal.String",
            "io.github.hwolf.kvalidation.Equal.test",
            "io.github.hwolf.kvalidation.Equal")
    }

    test("Order of codes for indexed property") {

        // When
        val actual = DefaultMessageCodeResolver(ConstraintViolation(
            propertyPath = listOf(PropertyName("test", "idx")),
            propertyType = PropertyType("String"),
            propertyValue = "xxx",
            constraint = Equal(1234)))

        // Then
        expectThat(actual).containsExactly(
            "io.github.hwolf.kvalidation.Equal.test[idx].String",
            "io.github.hwolf.kvalidation.Equal.test.String",
            "io.github.hwolf.kvalidation.Equal.String",
            "io.github.hwolf.kvalidation.Equal.test[idx]",
            "io.github.hwolf.kvalidation.Equal.test",
            "io.github.hwolf.kvalidation.Equal")
    }

    test("Order of codes with missing property type") {

        // When
        val actual = DefaultMessageCodeResolver(ConstraintViolation(
            propertyPath = listOf(PropertyName("test")),
            propertyType = null,
            propertyValue = "xxx",
            constraint = Equal(1234)))

        // Then
        expectThat(actual).containsExactly(
            "io.github.hwolf.kvalidation.Equal.test",
            "io.github.hwolf.kvalidation.Equal")
    }
})
