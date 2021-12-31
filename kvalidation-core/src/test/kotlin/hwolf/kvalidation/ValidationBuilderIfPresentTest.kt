package hwolf.kvalidation

import org.junit.jupiter.api.Test
import strikt.api.expectThat

class ValidationBuilderIfPresentTest {

    class TestBean(val field: String?)

    private val validator = validator<TestBean> {
        TestBean::field ifPresent {
            isIn("x1", "x2")
        }
    }

    @Test
    fun `Optional field is not set`() {
        expectThat(validator.validate(TestBean(field = null))).isValid()
    }

    @Test
    fun `Optional field is set but value is invalid`() {
        expectThat(validator.validate(TestBean(field = "xx")))
            .hasExactlyViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("field")),
                propertyType = PropertyType("String"),
                propertyValue = "xx",
                constraint = In(allowedValues = listOf("x1", "x2"))
            ))
    }

    @Test
    fun `Optional field is set and value is valid`() {
        expectThat(validator.validate(TestBean(field = "x1"))).isValid()
    }
}
