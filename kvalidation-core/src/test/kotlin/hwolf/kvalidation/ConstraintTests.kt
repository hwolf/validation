package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ConstraintTests : FunSpec({
    test("Message key is hwolf.validation.TestConstraint") {
        val constraint = TestConstraint(prop1 = "Value 1", prop2 = null)
        expectThat(constraint.messageKey).isEqualTo("hwolf.kvalidation.TestConstraint")
    }
})
