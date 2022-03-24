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

import io.github.hwolf.kvalidation.ConstraintViolation
import io.github.hwolf.kvalidation.PropertyName
import io.github.hwolf.kvalidation.PropertyType
import io.github.hwolf.kvalidation.validate
import io.github.hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class CreditCardTests : FunSpec({

    val mastercard = "5105105105105100"
    val visa = "4012888888881881"
    val amex = "378282246310005"
    val discovers = "6011111111111117"
    val diners = "30569309025904"

    data class CreditCardBean(val card: String)

    context("validate credit card") {

        val validator = validator<CreditCardBean> { CreditCardBean::card { creditCard() } }

        context("is valid credit card") {
            withData(mastercard, visa, amex, discovers) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).isValid()
            }
        }
        context("is invalid credit card") {

            withData("4417123456789112", "441712345678X112") { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).hasViolations(ConstraintViolation(
                    propertyPath = listOf(PropertyName("card")),
                    propertyType = PropertyType("String"),
                    propertyValue = value,
                    constraint = CreditorCard(setOf(
                        CreditorCard.Type.AMEX,
                        CreditorCard.Type.VISA,
                        CreditorCard.Type.MASTERCARD,
                        CreditorCard.Type.DISCOVER))))
            }
        }
    }

    context("Validate mastercard") {

        val validator =
            validator<CreditCardBean> { CreditCardBean::card { creditCard(CreditorCard.Type.MASTERCARD) } }

        context("is valid mastercard") {
            withData(listOf(mastercard)) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).isValid()
            }
        }
        context("is invalid mastercard") {
            withData(visa, amex, discovers) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).hasViolations(ConstraintViolation(
                    propertyPath = listOf(PropertyName("card")),
                    propertyType = PropertyType("String"),
                    propertyValue = value,
                    constraint = CreditorCard(setOf(CreditorCard.Type.MASTERCARD))))
            }
        }
    }

    context("Validate diners card") {

        val validator = validator<CreditCardBean> { CreditCardBean::card { creditCard(CreditorCard.Type.DINERS) } }

        context("is valid diners card") {
            withData(listOf(diners)) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).isValid()
            }
        }
        context("is invalid diners card") {
            withData(mastercard, visa, amex, discovers) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).hasViolations(ConstraintViolation(
                    propertyPath = listOf(PropertyName("card")),
                    propertyType = PropertyType("String"),
                    propertyValue = value,
                    constraint = CreditorCard(setOf(CreditorCard.Type.DINERS))))
            }
        }
    }
})
