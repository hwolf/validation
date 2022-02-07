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
package hwolf.kvalidation.common

import hwolf.kvalidation.validate
import hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty

class IbanTests : FunSpec({

    data class TestBean(val iban: String)

    val validator = validator<TestBean> { TestBean::iban { isIban() } }

    context("valid IBANs") {
        withData(TestBean("AT611904300234573201"), TestBean("DE89370400440532013000")) {
            val actual = validator.validate(it)
            expectThat(actual.violations).isEmpty()
        }
    }
    context("invalid IBANs") {
        withData(TestBean("At611904300234573201"), TestBean("DE99370400440532013000")) {
            val actual = validator.validate(it)
            expectThat(actual.violations).hasSize(1)
        }
    }
})
