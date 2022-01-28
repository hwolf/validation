package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class NotEmptyCollectionTests : FunSpec({

    val validator = validator<ListBean> { ListBean::list { isNotEmpty() } }

    context("is not empty") {
        withData(nameFn = Any::toString, listOf("x1", "x2"), listOf("")) { value ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is empty") {
        withData(nameFn = Any::toString, listOf(emptyList<String>())) { value ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("list")),
                propertyType = PropertyType("List"),
                propertyValue = value,
                constraint = NotEmpty))
        }
    }
})