package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class GreaterOrEqualTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isGreaterOrEqual(4) } }

    context("is greater than") {
        withData(4, 5) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    context("is not greater than") {
        withData(-1, 3) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = value,
                constraint = GreaterOrEqual(4)))
        }
    }
})