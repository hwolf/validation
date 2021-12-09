package hwolf.validation

import hwolf.validation.constraints.In
import hwolf.validation.constraints.isIn
import hwolf.validation.utils.hasExactlyViolations
import hwolf.validation.utils.isValid
import org.junit.jupiter.api.Test
import strikt.api.expectThat

class ValidationBuilderEachMapTest {

    class TestBean(val map: Map<String, Int>)

    private val validator = validator<TestBean> {
        TestBean::map each {
            isIn(1, 3)
        }
    }

    @Test
    fun `An empty map is valid`() {
        expectThat(validator.validator(TestBean(map = emptyMap()))).isValid()
    }

    @Test
    fun `One value in the map is invalid`() {
        expectThat(validator.validator(TestBean(map = mapOf("x1" to 1, "x2" to 2, "x3" to 3))))
            .hasExactlyViolations(
                ConstraintViolation(
                    propertyName = "map[x2]",
                    propertyType = "Int",
                    propertyValue = 2,
                    constraint = In(allowedValues = listOf(1, 3))
                ))
    }
}
