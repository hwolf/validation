package hwolf.kvalidation

import org.junit.jupiter.api.Test
import strikt.api.expectThat

class ValidationBuilderTest {

    @Test
    fun use() {
        val parent = validator<TestBean> {
            TestBean::prop1 { isEqual(1212) }
        }
        val validator = validator<TestBean> {
            use(parent)
        }
        val actual = validator.validate(TestBean(2121))
        expectThat(actual).hasViolations(
            ConstraintViolation(
                propertyName = "prop1",
                propertyType = PropertyType("Int"),
                propertyValue = 2121,
                constraint = Equal(value = 1212)))
    }
}
