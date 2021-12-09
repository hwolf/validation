package hwolf.validation

import hwolf.validation.constraints.In
import hwolf.validation.constraints.isIn
import hwolf.validation.utils.hasExactlyViolations
import hwolf.validation.utils.isValid
import org.junit.jupiter.api.Test
import strikt.api.expectThat

class ValidationBuilderEachArrayTest {

    class TestBean(val array: Array<String>)

    private val validator = validator<TestBean> {
        TestBean::array each {
            isIn("1", "3")
        }
    }

    @Test
    fun `An empty array is valid`() {
        expectThat(validator.validator(TestBean(array = emptyArray()))).isValid()
    }

    @Test
    fun `One value in the array is invalid`() {
        expectThat(validator.validator(TestBean(array = arrayOf("1", "2", "3"))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyName = "array[1]",
                    propertyType = "String",
                    propertyValue = "2",
                    constraint = In(allowedValues = listOf("1", "3"))
                ))
    }
}
