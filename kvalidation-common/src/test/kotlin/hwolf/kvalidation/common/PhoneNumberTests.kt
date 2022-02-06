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

import hwolf.kvalidation.Validator
import hwolf.kvalidation.validate
import hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isNotEmpty

class PhoneNumberTests : FunSpec({

    data class TestBean(val phoneNumber: String)

    fun doTest(phoneNumber: String, validator: Validator<TestBean>) =
        validator.validate(TestBean(phoneNumber)).violations

    context("Phone numbers with default region Germany") {

        val validator = validator<TestBean> { TestBean::phoneNumber { isPhoneNumber("DE") } }

        withData(
            "06221383250",
            "06221 383250",
            "06221/383250",
            "00 49 6221 383250",
            "+49 6221 383250",
            "+61403123456",
            "475465HELLO",
            "00000"
        ) {
            expectThat(doTest(it, validator)).isEmpty()
        }

        withData(
            "Pli plo plu",
            "00 49 6221 383((()=/()//(&250"
        ) {
            expectThat(doTest(it, validator)).isNotEmpty()
        }
    }

    context("Only possible phone numbers for Germany") {

        val validator = validator<TestBean> {
            TestBean::phoneNumber {
                isPhoneNumber("DE", PhoneNumber.Option.OnlyForRegion)
            }
        }

        withData(
            "06221383250",
            "06221 383250",
            "06221/383250",
            "00 49 6221 383250",
            "+49 6221 383250",
            "00000"
        ) {
            expectThat(doTest(it, validator)).isEmpty()
        }
    }

    context("Valid phone numbers with default region Germany") {

        val validator = validator<TestBean> {
            TestBean::phoneNumber {
                isPhoneNumber("DE", PhoneNumber.Option.Valid)
            }
        }

        withData(
            "06221383250",
            "06221 383250",
            "06221/383250",
            "00 49 6221 383250",
            "+49 6221 383250",
            "+61403123456",
            "475465TEST"
        ) {
            expectThat(doTest(it, validator)).isEmpty()
        }
    }

    context("Valid phone numbers for Germany") {

        val validator = validator<TestBean> {
            TestBean::phoneNumber {
                isPhoneNumber("DE", PhoneNumber.Option.Valid, PhoneNumber.Option.OnlyForRegion)
            }
        }

        withData(
            "06221383250",
            "06221 383250",
            "06221/383250",
            "00 49 6221 383250",
            "+49 6221 383250",
            "475465HALLO"
        ) {
            expectThat(doTest(it, validator)).isEmpty()
        }
    }

    context("Valid mobile phone numbers for Germany") {

        val validator = validator<TestBean> {
            TestBean::phoneNumber {
                isPhoneNumber("DE",
                    PhoneNumber.Option.Valid,
                    PhoneNumber.Option.OnlyForRegion,
                    PhoneNumber.Option.Mobile)
            }
        }
        withData(
            "01521 5123456",
            "173 3453645",
            "+49 173 3463TEST"
        ) {
            expectThat(doTest(it, validator)).isEmpty()
        }
        withData(
            "06221383250",
            "06221 383250",
            "06221/383250",
            "00 49 6221 383250",
            "+49 6221 383250",
            "475465HALLO"
        ) {
            expectThat(doTest(it, validator)).isNotEmpty()
        }
    }
})
