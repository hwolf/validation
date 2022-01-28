package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class LessOrEqualWithTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isLessOrEqual(TestBean::prop2) } }

    context("is less or equal than") {
        withData(TestBean(20, prop2 = 21), TestBean(23, prop2 = 23), TestBean(20, prop2 = null)) { bean ->
            val actual = validator.validate(bean)
            expectThat(actual).isValid()
        }
    }
    context("is not less or equal than") {
        withData(TestBean(143, prop2 = 22), TestBean(23, prop2 = 22)) { bean ->
            val actual = validator.validate(bean)
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = bean.prop1,
                constraint = LessOrEqualWith("prop2")))
        }
    }
})