package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat
import java.util.*

class EmptyArrayTests : FunSpec({

    val validator = validator<ArrayBean> { ArrayBean::array { isEmpty() } }

    context("is empty") {
        withData(nameFn = Array<String>::contentToString, listOf(emptyArray())) { value ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is not empty") {
        withData(nameFn = Array<String>::contentToString, arrayOf("x1", "x2"), arrayOf("")) { value ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("array")),
                propertyType = PropertyType("Array"),
                propertyValue = value,
                constraint = Empty))
        }
    }
})