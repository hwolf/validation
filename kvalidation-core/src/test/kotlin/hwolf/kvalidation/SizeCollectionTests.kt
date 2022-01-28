package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class SizeCollectionTests : FunSpec({

    val validator = validator<ListBean> { ListBean::list { hasSize(1, 4) } }

    context("has size") {
        withData(nameFn = Any::toString, listOf("x"), listOf("1", "2", "3", "4")) { value ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).isValid()
        }
    }
    context("has not size") {
        withData(nameFn = Any::toString, emptyList(), listOf("1", "2", "3", "4", "5")) { value ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("list")),
                propertyType = PropertyType("List"),
                propertyValue = value,
                constraint = Size(1, 4)))
        }
    }
})