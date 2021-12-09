package hwolf.validation

import hwolf.validation.constraints.In
import hwolf.validation.constraints.isIn
import hwolf.validation.utils.hasExactlyViolations
import hwolf.validation.utils.isValid
import org.junit.jupiter.api.Test
import strikt.api.expectThat

class ValidationBuilderEachTest {

    class TestBean(val list: List<String>)

    private val validator = validator<TestBean> {
        TestBean::list each {
            isIn("x1", "x3")
        }
    }

    @Test
    fun `An empty list is valid`() {
        expectThat(validator.validator(TestBean(list = emptyList()))).isValid()
    }

    @Test
    fun `One value in the list is invalid`() {
        expectThat(validator.validator(TestBean(list = listOf("x1", "x2", "x3"))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyName = "list[1]",
                    propertyType = "String",
                    propertyValue = "x2",
                    constraint = In(allowedValues = listOf("x1", "x3"))
                ))
    }
}
