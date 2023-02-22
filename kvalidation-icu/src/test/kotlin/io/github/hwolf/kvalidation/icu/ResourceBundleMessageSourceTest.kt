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

import io.github.hwolf.kvalidation.Locale
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class ResourceBundleMessageSourceTest : FunSpec({

    val sut = ResourceBundleMessageSource(ResourceBundleMessageSourceTest::class.qualifiedName!!)

    context("resolve message for locale") {
        Locale.setDefault(Locale.ROOT)
        withData(
            Triple("code-de-DE", Locale("de", "DE"), "found-de-DE"),
            Triple("code-de-DE", Locale("de"), "found-de"),
            Triple("code-de-DE", Locale("en", "US"), "found-en"),
            Triple("code-de-DE", Locale("en"), "found-en"),
            Triple("unknown-code", Locale("de", "DE"), null)
        ) { (code, locale, expected) ->
            sut(code, locale) shouldBe expected
        }

        test("resolve message several times") {
            sut("code-de-DE", Locale.GERMAN) shouldBe sut("code-de-DE", Locale.GERMAN)
        }
    }
})
