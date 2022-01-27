package hwolf.kvalidation.icu

import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.Equal
import hwolf.kvalidation.Locale
import hwolf.kvalidation.PropertyName
import hwolf.kvalidation.PropertyType
import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class DefaultMessageFormatterTest : FunSpec({

    test("substitute parameters from constraint") {

        // When
        val actual = DefaultMessageFormatter(
            "X {value} X",
            constraintViolation(),
            Locale.GERMAN)

        // Then
        expectThat(actual).isEqualTo("X 1.234 X")
    }

    test("substitute parameters propertyValue and propertyType") {

        // When
        val actual = DefaultMessageFormatter(
            "X {propertyValue}/{propertyType} X",
            constraintViolation(),
            Locale.GERMAN)

        // Then
        expectThat(actual).isEqualTo("X xxx/String X")
    }
})

private fun constraintViolation() = ConstraintViolation(
    propertyPath = listOf(PropertyName("test")),
    propertyType = PropertyType("String"),
    propertyValue = "xxx",
    constraint = Equal(1234))
