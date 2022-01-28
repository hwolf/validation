package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class EqualTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isEqual(2) } }

    context("is equal") {
        withData(listOf(2)) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    context("is not equal") {
        withData(0, 3) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = value,
                constraint = Equal(2)))
        }
    }
})