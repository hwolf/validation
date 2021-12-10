package hwolf.validation.constraints.ext

import hwolf.validation.validator
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class EmailTest {

    data class TestBean(val email: String)

    @TestFactory
    fun isEmail(): Collection<DynamicTest> = buildTests(
        validator = validator { TestBean::email { isEmail() } },
        validExamples = listOf(TestBean("xy.xx@abc.de")),
        invalidExamples = listOf(TestBean("xx.y@"), TestBean("xy.xx@localhost")))
}
