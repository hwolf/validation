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
package io.github.hwolf.kvalidation

typealias Locale = java.util.Locale
typealias MessageSource = (code: String, locale: Locale) -> String?
typealias MessageFormatter = (template: String, violation: ConstraintViolation, locale: Locale) -> String
typealias MessageCodeResolver = (violation: ConstraintViolation) -> List<String>

fun messageSourceMap(vararg messages: Pair<String, String>) = messageSourceMap(mapOf(*messages))
fun messageSourceMap(messages: Map<String, String>): MessageSource = { code, _ -> messages[code] }

class MessageInterpolator(
    private val messageSource: MessageSource,
    private val messageFormatter: MessageFormatter,
    private val messageCodeResolver: MessageCodeResolver = DefaultMessageCodeResolver
) {
    fun interpolate(violation: ConstraintViolation, locale: Locale): String =
        messageFormatter(
            findMessageTemplate(violation.constraint.messageKey, messageCodeResolver(violation), locale),
            violation,
            locale)

    private fun findMessageTemplate(defaultMessage: String, codes: Iterable<String>, locale: Locale) =
        codes.firstNotNullOfOrNull { code ->
            messageSource(code, locale)
        } ?: defaultMessage
}
