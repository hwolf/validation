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

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import io.github.hwolf.kvalidation.Constraint
import io.github.hwolf.kvalidation.ValidationBuilder
import io.github.hwolf.kvalidation.validate

/** A constraint that validate if the value is a phone number. */
data class PhoneNumber(
    val region: String,
    val options: Collection<Option>
) : Constraint {
    @Suppress("unused")
    enum class Option(val isPhoneTypeOption: Boolean = false) {
        Valid,
        OnlyForRegion,
        FixedLine(true),
        Mobile(true),
        FixedLineOrMobile(true),
        TollFree(true),
        PremiumRate(true),
        SharedCost(true),
        VOIP(true),
        PersonalNumber(true),
        Pager(true),
        UAN(true),
        VoiceMail(true),
        Unknown(true)
    }
}

/** Validates if the property value is a phone number. */
fun <T> ValidationBuilder<T, String>.phoneNumber(region: String, vararg options: PhoneNumber.Option) =
    phoneNumber(region, options.toList())

/** Validates if the property value is a phone number. */
fun <T> ValidationBuilder<T, String>.phoneNumber(region: String, options: Collection<PhoneNumber.Option>) =
    PhoneNumber(region, options).let { constraint ->
        validate(constraint) { v, _ ->
            PhoneNumberValidator.validate(v, constraint)
        }
    }

private object PhoneNumberValidator {

    private val util = PhoneNumberUtil.getInstance()

    private val types = mapOf(
        PhoneNumberUtil.PhoneNumberType.FIXED_LINE to PhoneNumber.Option.FixedLine,
        PhoneNumberUtil.PhoneNumberType.MOBILE to PhoneNumber.Option.Mobile,
        PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE to PhoneNumber.Option.FixedLineOrMobile,
        PhoneNumberUtil.PhoneNumberType.TOLL_FREE to PhoneNumber.Option.TollFree,
        PhoneNumberUtil.PhoneNumberType.PREMIUM_RATE to PhoneNumber.Option.PremiumRate,
        PhoneNumberUtil.PhoneNumberType.SHARED_COST to PhoneNumber.Option.SharedCost,
        PhoneNumberUtil.PhoneNumberType.VOIP to PhoneNumber.Option.VOIP,
        PhoneNumberUtil.PhoneNumberType.PERSONAL_NUMBER to PhoneNumber.Option.PersonalNumber,
        PhoneNumberUtil.PhoneNumberType.PAGER to PhoneNumber.Option.Pager,
        PhoneNumberUtil.PhoneNumberType.UAN to PhoneNumber.Option.UAN,
        PhoneNumberUtil.PhoneNumberType.VOICEMAIL to PhoneNumber.Option.VoiceMail,
        PhoneNumberUtil.PhoneNumberType.UNKNOWN to PhoneNumber.Option.Unknown)

    fun validate(phoneNumber: String, constraint: PhoneNumber): Boolean {
        val parsed = try {
            util.parse(phoneNumber, constraint.region)
        } catch (ex: NumberParseException) {
            return false
        }
        return DefaultOptionValidator.values()
            .filter { it.accept(constraint) }
            .all { it.validateOption(parsed, constraint) }
    }

    interface OptionValidator {
        fun accept(constraint: PhoneNumber): Boolean
        fun validateOption(number: Phonenumber.PhoneNumber, constraint: PhoneNumber): Boolean
    }

    enum class DefaultOptionValidator : OptionValidator {
        Possible {
            override fun accept(constraint: PhoneNumber) = true
            override fun validateOption(number: Phonenumber.PhoneNumber, constraint: PhoneNumber) =
                util.isPossibleNumber(number)
        },
        Valid {
            override fun accept(constraint: PhoneNumber) = PhoneNumber.Option.Valid in constraint.options
            override fun validateOption(number: Phonenumber.PhoneNumber, constraint: PhoneNumber) =
                util.isValidNumber(number)
        },
        OnlyForRegion {
            override fun accept(constraint: PhoneNumber) = PhoneNumber.Option.OnlyForRegion in constraint.options
            override fun validateOption(number: Phonenumber.PhoneNumber, constraint: PhoneNumber) =
                constraint.region == util.getRegionCodeForNumber(number)
        },
        PossibleForTypes {
            override fun accept(constraint: PhoneNumber) = constraint.options.hasPhoneType()
            override fun validateOption(number: Phonenumber.PhoneNumber, constraint: PhoneNumber) =
                types[util.getNumberType(number)] in constraint.options
        }
    }
}

private fun Iterable<PhoneNumber.Option>.hasPhoneType() = any { it.isPhoneTypeOption }
