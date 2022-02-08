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
package io.github.hwolf.kvalidation.icu

import com.ibm.icu.text.MessageFormat
import io.github.hwolf.kvalidation.Constraint
import io.github.hwolf.kvalidation.ConstraintViolation
import io.github.hwolf.kvalidation.Locale
import io.github. hwolf.kvalidation.MessageFormatter
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

object DefaultMessageFormatter : MessageFormatter {

    override fun invoke(template: String, violation: ConstraintViolation, locale: Locale): String =
        MessageFormat(template, locale).format(buildArguments(violation))

    private fun buildArguments(violation: ConstraintViolation): Map<String, Any?> =
        getArguments(violation.constraint, Constraint::class) +
                getArguments(violation, Any::class)

    private fun getArguments(bean: Any, base: KClass<*>): Map<String, Any?> =
        bean::class.memberProperties
            .filter { property ->
                base.declaredMemberProperties.none { it.name == property.name }
            }
            .associate { property ->
                property.name to property.call(bean)
            }
}
