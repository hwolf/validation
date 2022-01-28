package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class GreaterOrEqualWithTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isGreaterOrEqual(TestBean::prop2) } }

    context("is greater or equal") {
        withData(TestBean(-1, prop2 = -1), TestBean(-4, prop2 = -5), TestBean(-4, prop2 = null)) { bean ->
            val actual = validator.validate(bean)
            expectThat(actual).isValid()
        }
    }
    context("is not greater or equal") {
        withData(TestBean(0, prop2 = 5), TestBean(4, prop2 = 5)) { bean ->
            val actual = validator.validate(bean)
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = bean.prop1,
                constraint = GreaterOrEqualWith("prop2")))
        }
    }
})