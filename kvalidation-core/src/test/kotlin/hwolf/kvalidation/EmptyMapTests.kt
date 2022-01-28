package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class EmptyMapTests : FunSpec({

    val validator = validator<MapBean> { MapBean::map { isEmpty() } }

    context("is empty") {
        withData(listOf(emptyMap<String, Int>())) { value ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is not empty") {
        withData(mapOf("x1" to 2, "x2" to 3), mapOf("" to 0)) { value ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("map")),
                propertyType = PropertyType("Map"),
                propertyValue = value,
                constraint = Empty))
        }
    }
})