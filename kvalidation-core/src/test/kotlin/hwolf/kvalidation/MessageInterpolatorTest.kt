package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class MessageInterpolatorTest : FunSpec({

    fun constraintViolation() = ConstraintViolation(
        propertyPath = listOf(PropertyName("test")),
        propertyType = PropertyType("String"),
        propertyValue = "xxx",
        constraint = Equal(1234))

    context("Order of codes for simple property") {
        withData(
            Pair(messageSourceMap(
                "a" to "Level 3",
                "a.b" to "Level 2",
                "a.b.c" to "Level 1"),
                "Level 1"),
            Pair(messageSourceMap(
                "a" to "Level 3",
                "a.b" to "Level 2"),
                "Level 2"),
            Pair(messageSourceMap(
                "a" to "Level 3"),
                "Level 3"),
            Pair(messageSourceMap(),
                "hwolf.kvalidation.Equal")
        ) { (messageSource, expected) ->
            val sut = MessageInterpolator(
                messageSource = messageSource,
                messageFormatter = { template, _, _ -> template },
                messageCodeResolver = { listOf("a.b.c", "a.b", "a") })
            val actual = sut.interpolate(constraintViolation(), Locale.GERMAN)
            expectThat(actual).isEqualTo(expected)
        }
    }
})
