package hwolf.kvalidation.common

import hwolf.kvalidation.validator
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class UrlTest {

    data class TestBean(val url: String)

    @TestFactory
    fun isUrl(): Collection<DynamicTest> = buildTests(
        validator = validator { TestBean::url { isUrl() } },
        validExamples = listOf(TestBean("https://www.google.com")),
        invalidExamples = listOf(TestBean("aaa."), TestBean("go.1aa"))
    )
}
