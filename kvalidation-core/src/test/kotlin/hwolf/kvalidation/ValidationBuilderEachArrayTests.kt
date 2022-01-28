package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat

class ValidationBuilderEachArrayTests : FunSpec({

    class TestBean(val array: Array<String>)

    val validator = validator<TestBean> {
        TestBean::array each {
            isIn("1", "3")
        }
    }

    test("An empty array is valid") {
        expectThat(validator.validate(TestBean(array = emptyArray()))).isValid()
    }

    test("One value in the array is invalid") {
        expectThat(validator.validate(TestBean(array = arrayOf("1", "2", "3"))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyPath = listOf(PropertyName("array", 1)),
                    propertyType = PropertyType("String"),
                    propertyValue = "2",
                    constraint = In(allowedValues = listOf("1", "3"))
                ))
    }
})
