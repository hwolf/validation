package hwolf.kvalidation.common

import hwolf.kvalidation.validator
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class IbanTest {

    data class TestBean(val iban: String)

    @TestFactory
    fun isIban(): Collection<DynamicTest> = buildTests(
        validator = validator { TestBean::iban { isIban() } },
        validExamples = listOf(TestBean("AT611904300234573201"), TestBean("DE89370400440532013000")),
        invalidExamples = listOf(TestBean("At611904300234573201"), TestBean("DE99370400440532013000"))
    )
}
