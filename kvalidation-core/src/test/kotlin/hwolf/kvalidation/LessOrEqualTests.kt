package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class LessOrEqualTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isLessOrEqual(23) } }

    context("is less or equal than") {
        withData(22, 23) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    context("is not less or equal than") {
        withData(24, 30) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = value,
                constraint = LessOrEqual(23)))
        }
    }
})