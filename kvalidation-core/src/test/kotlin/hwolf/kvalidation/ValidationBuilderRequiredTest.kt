package hwolf.kvalidation

import org.junit.jupiter.api.Test
import strikt.api.expectThat

class ValidationBuilderRequiredTest {

    class TestBean(val field: String?)

    private val validator = validator<TestBean> {
        TestBean::field required {
            isIn("x1", "x2")
        }
    }

    @Test
    fun `Required field is not set`() {
        expectThat(validator.validate(TestBean(field = null)))
            .hasExactlyViolations(ConstraintViolation(
                propertyName = "field",
                propertyType = PropertyType("String"),
                propertyValue = null,
                constraint = Required
            ))
    }

    @Test
    fun `Required field is set but value is invalid`() {
        expectThat(validator.validate(TestBean(field = "xx")))
            .hasExactlyViolations(ConstraintViolation(
                propertyName = "field",
                propertyType = PropertyType("String"),
                propertyValue = "xx",
                constraint = In(allowedValues = listOf("x1", "x2"))
            ))
    }

    @Test
    fun `Required field is set and value is valid`() {
        expectThat(validator.validate(TestBean(field = "x2"))).isValid()
    }
}
