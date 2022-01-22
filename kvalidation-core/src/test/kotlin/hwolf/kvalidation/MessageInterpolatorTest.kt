package hwolf.kvalidation

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.MethodSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Suppress("MemberVisibilityCanBePrivate")
class MessageInterpolatorTest {

    @ParameterizedTest
    @MethodSource("messageSources")
    fun `Order of codes for simple property`(messageSource: MessageSource, expected: String) {

        // Given
        val sut = MessageInterpolator(
            messageSource = messageSource,
            messageFormatter = { template, _, _ -> template },
            messageCodeResolver = { _ -> listOf("a.b.c", "a.b", "a") })

        // When
        val actual = sut.interpolate(constraintViolation(), Locale.GERMAN)

        // Then
        expectThat(actual).isEqualTo(expected)
    }

    private fun constraintViolation() = ConstraintViolation(
        propertyPath = listOf(PropertyName("test")),
        propertyType = PropertyType("String"),
        propertyValue = "xxx",
        constraint = Equal(1234))

    fun messageSources() = listOf(
        of(messageSourceMap(
            "a" to "Level 3",
            "a.b" to "Level 2",
            "a.b.c" to "Level 1"),
            "Level 1"),
        of(messageSourceMap(
            "a" to "Level 3",
            "a.b" to "Level 2"),
            "Level 2"),
        of(messageSourceMap(
            "a" to "Level 3"),
            "Level 3"),
        of(messageSourceMap(),
            "hwolf.kvalidation.Equal"))
}
