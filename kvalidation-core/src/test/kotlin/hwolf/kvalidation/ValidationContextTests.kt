@file:Suppress("unused")

package hwolf.kvalidation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty
import strikt.assertions.map

private class ValidationContextTestBean(val prop1: ValidationContextTestBean? = null, val prop2: String = "")

@Testable
@Suppress("LongMethod")
fun `validation context`() = rootContext<ValidationContext<ValidationContextTestBean>> {

    given {
        ValidationContext(ValidationContextTestBean())
    }

    test("New instance has no errors") {
        expectThat(this.errors).isEmpty()
    }
    test("Add validation error") {
        constraintViolation(NotEmpty, "value")
        expectThat(errors).map { it.constraint }.containsExactly(NotEmpty)
    }
    test("propertyName - Add validation error with property") {
        val context = withProperty(ValidationContextTestBean::prop1, ValidationContextTestBean())
        context.constraintViolation(NotEmpty, "value")
        expectThat(context.errors).containsExactly(ConstraintViolation(
            propertyName = "prop1",
            propertyType = PropertyType("ValidationContextTestBean"),
            propertyValue = "value",
            constraint = NotEmpty))
    }
    test("propertyName - Add validation error with property path") {
        val context = withProperty(ValidationContextTestBean::prop1, ValidationContextTestBean())
            .withProperty(ValidationContextTestBean::prop2, ValidationContextTestBean())
        context.constraintViolation(NotEmpty, "value")
        expectThat(context.errors).containsExactly(ConstraintViolation(
            propertyName = "prop1.prop2",
            propertyType = PropertyType("String"),
            propertyValue = "value",
            constraint = NotEmpty))
    }

    test("propertyName - Add validation error with property and key") {
        val context = withProperty(ValidationContextTestBean::prop2, ValidationContextTestBean(), "key", 12)
        context.constraintViolation(NotEmpty, "value")
        expectThat(context.errors).containsExactly(ConstraintViolation(
            propertyName = "prop2[key]",
            propertyType = PropertyType("Int"),
            propertyValue = "value",
            constraint = NotEmpty))
    }

    test("propertyName - Pop") {
        val context = withProperty(ValidationContextTestBean::prop2, ValidationContextTestBean(), "key", "")
        context.withProperty(ValidationContextTestBean::prop1, ValidationContextTestBean())
            .constraintViolation(NotBlank, "value")
        context.constraintViolation(NotEmpty, "value")
        expectThat(context.errors).map { Pair(it.constraint, it.propertyName) }
            .containsExactly(
                Pair(NotBlank, "prop2[key].prop1"),
                Pair(NotEmpty, "prop2[key]"))
    }

    test("propertyType - Add validation error with property") {
        val context = withProperty(ValidationContextTestBean::prop1, ValidationContextTestBean())
        context.constraintViolation(NotEmpty, "value")
        expectThat(context.errors).map { it.propertyType }
            .containsExactly(PropertyType("ValidationContextTestBean"))
    }

    test("propertyType - Add validation error with property path") {
        val context = withProperty(property = ValidationContextTestBean::prop1,
            value = ValidationContextTestBean())
            .withProperty(ValidationContextTestBean::prop2, ValidationContextTestBean())
        context.constraintViolation(NotEmpty, "value")
        expectThat(context.errors).map { it.propertyType }
            .containsExactly(PropertyType("String"))
    }

    test("propertyType - Add validation error with property and key") {
        val context = withProperty(property = ValidationContextTestBean::prop2,
            collection = ValidationContextTestBean(),
            key = "key",
            value = 45)
        context.constraintViolation(NotEmpty, "value")
        expectThat(context.errors).map { it.propertyType }
            .containsExactly(PropertyType("Int"))
    }
}
