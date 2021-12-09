package hwolf.validation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat
import strikt.assertions.doesNotContainKeys
import strikt.assertions.hasEntry
import strikt.assertions.isEqualTo

@Testable
fun `Constraint`() = rootContext<TestConstraint> {
    given {
        TestConstraint(prop1 = "Value 1", prop2 = null)
    }
    test("Message key is hwolf.validation.ATestConstraint.message") {
        expectThat(this.messageKey).isEqualTo("hwolf.validation.TestConstraint.message")
    }
    test("Message parameters contains property prop1") {
        expectThat(this.parameters).hasEntry("prop1", "Value 1").hasEntry("prop2", null)
    }
    test("Message parameters does not contain properties from interface Constraint") {
        expectThat(this.parameters).doesNotContainKeys("messageKey", "parameters")
    }
}
