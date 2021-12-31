package hwolf.kvalidation

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
        expectThat(validator.validate(TestBean(list = emptyList()))).isValid()
    }

    @Test
    fun `One value in the list is invalid`() {
        expectThat(validator.validate(TestBean(list = listOf("x1", "x2", "x3"))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyPath = listOf(PropertyName("list", 1)),
                    propertyType = PropertyType("String"),
                    propertyValue = "x2",
                    constraint = In(allowedValues = listOf("x1", "x3"))
                ))
    }
}
