package io.github.hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ConstraintViolationTests : FunSpec({

    fun constraintViolation(vararg propertyPath: PropertyName) = ConstraintViolation(
        propertyPath = propertyPath.toList(),
        propertyType = null,
        propertyValue = null,
        constraint = Required)

    test("property name") {
        expectThat(constraintViolation(PropertyName("part1", "key1"), PropertyName("part2")))
            .get(ConstraintViolation::propertyName)
            .get { this?.toString() }
            .isEqualTo("part2")
    }
    test("property name with key") {
        expectThat(constraintViolation(PropertyName("part1"), PropertyName("part2", "key2")))
            .get(ConstraintViolation::propertyName)
            .get { this?.toString() }
            .isEqualTo("part2[key2]")
    }
})
