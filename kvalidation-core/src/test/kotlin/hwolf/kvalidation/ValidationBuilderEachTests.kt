package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat

class ValidationBuilderEachTests : FunSpec({

    class TestBean(val list: List<String>)

    val validator = validator<TestBean> {
        TestBean::list each {
            isIn("x1", "x3")
        }
    }

    test("An empty list is valid") {
        expectThat(validator.validate(TestBean(list = emptyList()))).isValid()
    }

    test("One value in the list is invalid") {
        expectThat(validator.validate(TestBean(list = listOf("x1", "x2", "x3"))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyPath = listOf(PropertyName("list", 1)),
                    propertyType = PropertyType("String"),
                    propertyValue = "x2",
                    constraint = In(allowedValues = listOf("x1", "x3"))
                ))
    }
})
