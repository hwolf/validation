package hwolf.kvalidation.i18n

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.*

class ResourceBundleMessageSourceTest : FunSpec({

    val sut = ResourceBundleMessageSource("hwolf.kvalidation.i18n.ResourceBundleMessageSourceTest")

    context("resolve message for locale") {
        withData(Triple("code-de-DE", Locale("de", "DE"), "found-de-DE"),
            Triple("code-de-DE", Locale("de"), "found-de"),
            Triple("code-de-DE", Locale("en", "US"), "found-en"),
            Triple("code-de-DE", Locale("en"), "found-en"),
            Triple("code-de-DE", Locale("fr"), "found-default"),
            Triple("unknown-code", Locale("de", "DE"), null)
        ) { (code, locale, expected) ->
            expectThat(sut(code, locale)).isEqualTo(expected)
        }
    }

    test("resolve message several times") {
        expectThat(sut("code-de-DE", Locale.GERMAN)).isEqualTo(sut("code-de-DE", Locale.GERMAN))
    }
})