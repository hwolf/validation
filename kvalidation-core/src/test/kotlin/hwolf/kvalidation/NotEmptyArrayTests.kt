package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class NotEmptyArrayTests : FunSpec({

    val validator = validator<ArrayBean> { ArrayBean::array { isNotEmpty() } }

    context("is not empty") {
        withData(nameFn = Array<String>::contentToString, arrayOf("x1", "x2"), arrayOf("")) { value ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is empty") {
        withData(nameFn = Array<String>::contentToString, listOf(emptyArray<String>())) { value ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("array")),
                propertyType = PropertyType("Array"),
                propertyValue = value,
                constraint = NotEmpty))
        }
    }
})