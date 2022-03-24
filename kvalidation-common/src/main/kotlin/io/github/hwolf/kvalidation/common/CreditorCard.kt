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

import io.github.hwolf.kvalidation.Constraint
import io.github.hwolf.kvalidation.ValidationBuilder
import io.github.hwolf.kvalidation.validate
import org.apache.commons.validator.routines.CreditCardValidator

/** A constraint that validate if the value is a credit card number. */
data class CreditorCard(
    val cardTypes: Collection<Type>
) : Constraint {

    enum class Type(
        internal val x: Long
    ) {
        /** Option specifying that American Express cards are allowed. */
        AMEX(CreditCardValidator.AMEX),

        /** Option specifying that Visa cards are allowed. */
        VISA(CreditCardValidator.VISA),

        /** Option specifying that Mastercard cards are allowed. */
        MASTERCARD(CreditCardValidator.MASTERCARD),

        /** Option specifying that Discover cards are allowed. */
        DISCOVER(CreditCardValidator.DISCOVER),

        /** Option specifying that Diners cards are allowed. */
        DINERS(CreditCardValidator.DINERS),

        /** Option specifying that VPay (Visa) cards are allowed. */
        VPAY(CreditCardValidator.VPAY)
    }
}

/** Validates if the property value is a credit card number. */
fun <T> ValidationBuilder<T, String>.creditCard() =
    creditCard(CreditorCard.Type.AMEX,
        CreditorCard.Type.VISA,
        CreditorCard.Type.MASTERCARD,
        CreditorCard.Type.DISCOVER)

/** Validates if the property value is a credit card number. */
fun <T> ValidationBuilder<T, String>.creditCard(vararg cardTypes: CreditorCard.Type) =
    creditCard(cardTypes.toSet())

/** Validates if the property value is a credit card number. */
fun <T> ValidationBuilder<T, String>.creditCard(cardTypes: Collection<CreditorCard.Type>) =
    validate(CreditorCard(cardTypes)) { v, _ ->
        CreditCardValidator(cardTypes.sumOf(CreditorCard.Type::x)).isValid(v)
    }
