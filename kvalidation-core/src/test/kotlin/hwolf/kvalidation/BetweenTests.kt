package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class BetweenTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isBetween(5, 7) } }

    context("is between") {
        withData(5, 6, 7) { value ->
            val actual = validator.validate(TestBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is not between") {
        withData(4, 8) { value ->
            val actual = validator.validate(TestBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = value,
                constraint = Between(5, 7)))
        }
    }
})