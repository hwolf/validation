package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class LessTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isLess(23) } }

    context("is less than") {
        withData(3, 22) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    context("is not less than") {
        withData(23, 24) { value ->
            val actual = validator.validate(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = value,
                constraint = Less(23)))
        }
    }
})