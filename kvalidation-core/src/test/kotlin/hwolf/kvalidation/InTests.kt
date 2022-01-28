package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class InTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isIn(1, 3) } }

    context("is in") {
        withData(1, 3) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    context("is not in") {
        withData(0, 2, 4) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = value,
                constraint = In(listOf(1, 3))))
        }
    }
})