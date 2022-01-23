package hwolf.kvalidation.i18n

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.*

class ResourceBundleMessageSourceTest {

    private val sut = ResourceBundleMessageSource("hwolf.kvalidation.i18n.ResourceBundleMessageSourceTest")

    @ParameterizedTest
    @CsvSource(value = [
        "code-de-DE, de, DE, found-de-DE",
        "code-de-DE, de, , found-de",
        "code-de-DE, en, US, found-en",
        "code-de-DE, en, , found-en",
        "code-de-DE, fr, , found-default",
        "unknown-code, de, DE, "])
    fun `resolve message for locale`(code: String, language: String, country: String?, expected: String?) {
        expectThat(sut(code, Locale(language, country ?: ""))).isEqualTo(expected)
    }

    @Test
    fun `resolve message several times`() {
        expectThat(sut("code-de-DE", Locale.GERMAN)).isEqualTo(sut("code-de-DE", Locale.GERMAN))
    }
}
