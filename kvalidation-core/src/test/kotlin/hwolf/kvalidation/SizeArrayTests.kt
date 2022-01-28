package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class SizeArrayTests : FunSpec({

    val validator = validator<ArrayBean> { ArrayBean::array { hasSize(1, 4) } }

    context("has size") {
        withData(arrayOf("x"), arrayOf("1", "2", "3", "4")) { value ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).isValid()
        }
    }
    context("has not size") {
        withData(arrayOf(), arrayOf("1", "2", "3", "4", "5")) { value ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("array")),
                propertyType = PropertyType("Array"),
                propertyValue = value,
                constraint = Size(1, 4)))
        }
    }
})
