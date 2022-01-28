package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class LessWithTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isLess(TestBean::prop2) } }

    context("is less than") {
        withData(TestBean(20, prop2 = 21), TestBean(20, prop2 = null)) { bean ->
            val actual = validator.validate(bean)
            expectThat(actual).isValid()
        }
    }
    context("is not less than") {
        withData(TestBean(23, prop2 = 23), TestBean(23, prop2 = 22)) { bean ->
            val actual = validator.validate(bean)
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = bean.prop1,
                constraint = LessWith("prop2")))
        }
    }
})