package hwolf.kvalidation.common

import hwolf.kvalidation.ValidationContext
import hwolf.kvalidation.Validator
import org.junit.jupiter.api.DynamicTest
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty

fun <V> buildTests(validator: Validator<V>, validExamples: List<V>, invalidExamples: List<V>) =
    validExamples.map {
        DynamicTest.dynamicTest("value <$it> is valid") {
            val context = ValidationContext(it)
            validator(it, context)
            expectThat(context.errors).isEmpty()
        }
    } + invalidExamples.map {
        DynamicTest.dynamicTest("value <$it> is invalid") {
            val context = ValidationContext(it)
            validator(it, context)
            expectThat(context.errors).hasSize(1)
        }
    }
