package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class GreaterTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isGreater(4) } }

    context("is greater than") {
        withData(5, 11) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    context("is not greater than") {
        withData(3, 4) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = value,
                constraint = Greater(4)))
        }
    }
})