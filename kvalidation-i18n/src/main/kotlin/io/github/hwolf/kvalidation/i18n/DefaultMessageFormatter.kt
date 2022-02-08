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
package io.github.hwolf.kvalidation.i18n

import com.mitchellbosecke.pebble.PebbleEngine
import io.github.hwolf.kvalidation.ConstraintViolation
import io.github.hwolf.kvalidation.Locale
import io.github.hwolf.kvalidation.MessageFormatter
import java.io.StringWriter

object DefaultMessageFormatter : MessageFormatter {

    private val engine = PebbleEngine.Builder().strictVariables(true).build()

    override fun invoke(template: String, violation: ConstraintViolation, locale: Locale) =
        StringWriter().apply {
            engine.getLiteralTemplate(template).evaluate(
                this,
                mapOf("violation" to violation, "constraint" to violation.constraint),
                locale)
        }.toString()
}
