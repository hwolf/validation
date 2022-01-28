package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class NotEmptyIterableTests : FunSpec({

    val validator = validator<IterableBean> { IterableBean::range { isNotEmpty() } }

    context("is not empty") {
        withData(IntRange(0, 1), IntRange(1, 1)) { value ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is empty") {
        withData(IntRange(1, 0), IntRange(2, -1)) { value ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("range")),
                propertyType = PropertyType("Iterable"),
                propertyValue = value,
                constraint = NotEmpty))
        }
    }
})