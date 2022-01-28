package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat

class ValidationBuilderTests : FunSpec( {

    test("use") {
        val parent = validator<TestBean> {
            TestBean::prop1 { isEqual(1212) }
        }
        val validator = validator<TestBean> {
            use(parent)
        }
        val actual = validator.validate(TestBean(2121))
        expectThat(actual).hasViolations(
            ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = 2121,
                constraint = Equal(value = 1212)))
    }
})
