@file:Suppress("unused")

package hwolf.kvalidation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.containsExactly
import strikt.assertions.doesNotContain
import strikt.assertions.isEmpty
import strikt.assertions.map

@Testable
fun testsValidationResult() = rootContext<ValidationResult> {

    fun violation(property: String, constraint: Constraint) = ConstraintViolation(
        propertyName = property,
        propertyType = "Any",
        propertyValue = null,
        constraint = constraint)

    given {
        ValidationResult(listOf(
            violation("prop1", Required),
            violation("prop1", NotBlank),
            violation("otherProp", Empty)))
    }
    test("Operator [] should return all violations for a property") {
        expectThat(this["prop1"]) {
            containsExactly(
                violation("prop1", Required),
                violation("prop1", NotBlank))
        }
    }
    test("Operator [] should return only violations for a property") {
        expectThat(this["prop1"]) {
            map(ConstraintViolation::propertyName)
                .contains("prop1")
                .doesNotContain("otherProp")
        }
    }
    test("Operator [] should return empty list for unknown property") {
        expectThat(this["unknownProp"]) {
            isEmpty()
        }
    }
}
