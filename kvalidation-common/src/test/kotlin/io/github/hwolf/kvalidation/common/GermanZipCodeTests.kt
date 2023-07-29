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
package io.github.hwolf.kvalidation.common

import io.github.hwolf.kvalidation.validate
import io.github.hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty

class GermanZipCodeTests : FunSpec({

    data class TestBean(val zipCode: String)

    val validator = validator { TestBean::zipCode { germanZipCode() } }

    context("valid German zip code") {
        val values = listOf(
            "01000", // Not valid, but easier regex
            "99999", // Not valid, but easier regex
            "01001", // First valid zip code
            "99998", // Last valid zip code
            "81927",
            "13465",
            "10123")
        withData(values) {
            val actual = validator.validate(TestBean(it))
            expectThat(actual.violations).isEmpty()
        }
    }
    context("invalid German zip code") {
        val values = listOf(
            "1234", // Zip code must have 5 digits
            "123456",
            "00000", // Zip code with 2 leading '0' is invalid
            "00123")
        withData(values) {
            val actual = validator.validate(TestBean(it))
            expectThat(actual.violations).hasSize(1)
        }
    }
})
