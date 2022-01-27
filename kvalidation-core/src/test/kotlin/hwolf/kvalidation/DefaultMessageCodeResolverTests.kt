package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat
import strikt.assertions.containsExactly

class DefaultMessageCodeResolverTests : FunSpec({

    test("Order of codes for simple property") {

        // When
        val actual = DefaultMessageCodeResolver(ConstraintViolation(
            propertyPath = listOf(PropertyName("test")),
            propertyType = PropertyType("String"),
            propertyValue = "xxx",
            constraint = Equal(1234)))

        // Then
        expectThat(actual).containsExactly(
            "hwolf.kvalidation.Equal.test.String",
            "hwolf.kvalidation.Equal.String",
            "hwolf.kvalidation.Equal.test",
            "hwolf.kvalidation.Equal")
    }

    test("Order of codes for indexed property") {

        // When
        val actual = DefaultMessageCodeResolver(ConstraintViolation(
            propertyPath = listOf(PropertyName("test", "idx")),
            propertyType = PropertyType("String"),
            propertyValue = "xxx",
            constraint = Equal(1234)))

        // Then
        expectThat(actual).containsExactly(
            "hwolf.kvalidation.Equal.test[idx].String",
            "hwolf.kvalidation.Equal.test.String",
            "hwolf.kvalidation.Equal.String",
            "hwolf.kvalidation.Equal.test[idx]",
            "hwolf.kvalidation.Equal.test",
            "hwolf.kvalidation.Equal")
    }

    test("Order of codes with missing property type") {

        // When
        val actual = DefaultMessageCodeResolver(ConstraintViolation(
            propertyPath = listOf(PropertyName("test")),
            propertyType = null,
            propertyValue = "xxx",
            constraint = Equal(1234)))

        // Then
        expectThat(actual).containsExactly(
            "hwolf.kvalidation.Equal.test",
            "hwolf.kvalidation.Equal")
    }
})
