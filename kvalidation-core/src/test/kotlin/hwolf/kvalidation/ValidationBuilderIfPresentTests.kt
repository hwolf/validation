package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat

class ValidationBuilderIfPresentTests : FunSpec({

    class TestBean(val field: String?)

    val validator = validator<TestBean> {
        TestBean::field ifPresent {
            isIn("x1", "x2")
        }
    }

    test("Optional field is not set") {
        expectThat(validator.validate(TestBean(field = null))).isValid()
    }

    test("Optional field is set but value is invalid") {
        expectThat(validator.validate(TestBean(field = "xx")))
            .hasExactlyViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("field")),
                propertyType = PropertyType("String"),
                propertyValue = "xx",
                constraint = In(allowedValues = listOf("x1", "x2"))
            ))
    }

    test("Optional field is set and value is valid") {
        expectThat(validator.validate(TestBean(field = "x1"))).isValid()
    }
})
