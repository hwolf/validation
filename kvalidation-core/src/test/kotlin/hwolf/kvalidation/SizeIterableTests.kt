package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class SizeIterableTests : FunSpec({

    val validator = validator<IterableBean> { IterableBean::range { hasSize(2, 4) } }

    context("has size") {
        withData(IntRange(0, 1), IntRange(0, 3)) { value ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).isValid()
        }
    }
    context("has not size") {
        withData(IntRange(2, 2), IntRange(0, 4)) { value ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("range")),
                propertyType = PropertyType("Iterable"),
                propertyValue = value,
                constraint = Size(2, 4)))
        }
    }
})