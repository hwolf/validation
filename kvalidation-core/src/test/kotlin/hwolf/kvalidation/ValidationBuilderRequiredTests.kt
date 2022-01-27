package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat

class ValidationBuilderRequiredTests: FunSpec({

    class TestBean(val field: String?)

    val validator = validator<TestBean> {
        TestBean::field required {
            isIn("x1", "x2")
        }
    }

    test("Required field is not set") {
        expectThat(validator.validate(TestBean(field = null)))
            .hasExactlyViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("field")),
                propertyType = PropertyType("String"),
                propertyValue = null,
                constraint = Required
            ))
    }

    test("Required field is set but value is invalid") {
        expectThat(validator.validate(TestBean(field = "xx")))
            .hasExactlyViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("field")),
                propertyType = PropertyType("String"),
                propertyValue = "xx",
                constraint = In(allowedValues = listOf("x1", "x2"))
            ))
    }

    test("Required field is set and value is valid") {
        expectThat(validator.validate(TestBean(field = "x2"))).isValid()
    }
})
