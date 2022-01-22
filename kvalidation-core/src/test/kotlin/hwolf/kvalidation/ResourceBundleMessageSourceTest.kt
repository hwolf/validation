package hwolf.kvalidation

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.Locale

class ResourceBundleMessageSourceTest {

    private val sut = ResourceBundleMessageSource("hwolf.kvalidation.ResourceBundleMessageSourceTest")

    @ParameterizedTest
    @CsvSource(value = [
        "code-de-DE, de, DE, found-de-DE",
        "code-de-DE, de, , found-de",
        "code-de-DE, en, US, found-en",
        "code-de-DE, en, , found-en",
        "code-de-DE, fr, , found-default",
        "unknown-code, de, DE, "])
    fun `resolve messages for locale`(code: String, language: String, country: String?, expected: String?) {
        expectThat(sut(code, Locale(language, country ?: ""))).isEqualTo(expected)
    }
}
