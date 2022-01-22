package hwolf.kvalidation

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class DefaultMessageFormatterTest {

    @Test
    fun `substitute parameters from constraint`() {

        // When
        val actual = DefaultMessageFormatter(
            "X {{constraint.value}} X",
            constraintViolation(),
            Locale.GERMAN)

        // Then
        expectThat(actual).isEqualTo("X 1234 X")
    }

    @Test
    fun `substitute parameters propertyValue and propertyType`() {

        // When
        val actual = DefaultMessageFormatter(
            "X {{violation.propertyValue}}/{{violation.propertyType}} X",
            constraintViolation(),
            Locale.GERMAN)

        // Then
        expectThat(actual).isEqualTo("X xxx/String X")
    }

    private fun constraintViolation() = ConstraintViolation(
        propertyPath = listOf(PropertyName("test")),
        propertyType = PropertyType("String"),
        propertyValue = "xxx",
        constraint = Equal(1234))
}
