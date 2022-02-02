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

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ValidationBuilder
import hwolf.kvalidation.validate
import org.apache.commons.validator.routines.EmailValidator

/** A constraint that validate if the value is an email. */
data class Email(
    val options: Collection<Options>
) : Constraint {
    enum class Options {

        /** Should local addresses be considered valid ? */
        AllowLocal
    }
}

/** Validates if the property value is an email. */
fun <T> ValidationBuilder<T, String>.isEmail() = isEmail(emptySet())

/** Validates if the property value is an email. */
fun <T> ValidationBuilder<T, String>.isEmail(vararg options: Email.Options) = isEmail(options.toSet())

/** Validates if the property value is an email. */
fun <T> ValidationBuilder<T, String>.isEmail(options: Collection<Email.Options>) =
    validate(Email(options)) { v, _ ->
        EmailValidator.getInstance(Email.Options.AllowLocal in options, false).isValid(v)
    }
