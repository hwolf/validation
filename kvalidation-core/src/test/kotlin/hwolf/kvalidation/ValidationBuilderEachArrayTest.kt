package hwolf.kvalidation

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
        expectThat(validator.validate(TestBean(array = emptyArray()))).isValid()
    }

    @Test
    fun `One value in the array is invalid`() {
        expectThat(validator.validate(TestBean(array = arrayOf("1", "2", "3"))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyName = "array[1]",
                    propertyType = PropertyType("String"),
                    propertyValue = "2",
                    constraint = In(allowedValues = listOf("1", "3"))
                ))
    }
}
