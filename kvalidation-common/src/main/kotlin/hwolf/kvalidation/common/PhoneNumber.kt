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

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ValidationBuilder
import hwolf.kvalidation.validate

/** A constraint that validate if the value is a phone number. */
data class PhoneNumber(
    val defaultRegion: String,
    val options: Collection<Options>
) : Constraint {
    enum class Options {
        Valid,
        OnlyForRegion
    }
}

/** Validates if the property value is a phone number. */
fun <T> ValidationBuilder<T, String>.isPhoneNumber(defaultRegion: String, vararg options: PhoneNumber.Options) =
    isPhoneNumber(defaultRegion, options.toSet())

/** Validates if the property value is a phone number. */
fun <T> ValidationBuilder<T, String>.isPhoneNumber(defaultRegion: String, options: Collection<PhoneNumber.Options>) =
    validate(PhoneNumber(defaultRegion, options)) { v, _ ->
        validatePhoneNumber(v, defaultRegion, options)
    }

private fun validatePhoneNumber(
    phoneNumber: String,
    defaultRegion: String,
    options: Collection<PhoneNumber.Options>,
    util: PhoneNumberUtil = PhoneNumberUtil.getInstance()
): Boolean {
    val parsed = try {
        util.parse(phoneNumber, defaultRegion)
    } catch (ex: NumberParseException) {
        return false
    }
    if (!util.isPossibleNumber(parsed)) {
        return false
    }
    if (PhoneNumber.Options.Valid in options && !util.isValidNumber(parsed)) {
        return false
    }
    if (PhoneNumber.Options.OnlyForRegion in options && defaultRegion != util.getRegionCodeForNumber(parsed)) {
        return false
    }
    return true
}
