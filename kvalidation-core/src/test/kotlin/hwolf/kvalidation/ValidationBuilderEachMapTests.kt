package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat

class ValidationBuilderEachMapTests : FunSpec({

    class TestBean(val map: Map<String, Int>)

    val validator = validator<TestBean> {
        TestBean::map each {
            isIn(1, 3)
        }
    }

    test("An empty map is valid") {
        expectThat(validator.validate(TestBean(map = emptyMap()))).isValid()
    }

    test("One value in the map is invalid") {
        expectThat(validator.validate(TestBean(map = mapOf("x1" to 1, "x2" to 2, "x3" to 3))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyPath = listOf(PropertyName("map", "x2")),
                    propertyType = PropertyType("Int"),
                    propertyValue = 2,
                    constraint = In(allowedValues = listOf(1, 3))
                ))
    }
})
