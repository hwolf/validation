package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class NotBetweenTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isNotBetween(5, 7) } }

    context("is nt between") {
        withData(4, 8) { value ->
            val actual = validator.validate(TestBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is between") {
        withData(5, 6, 7) { value ->
            val actual = validator.validate(TestBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = value,
                constraint = NotBetween(5, 7)))
        }
    }
})

