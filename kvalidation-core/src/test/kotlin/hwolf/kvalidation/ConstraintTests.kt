package hwolf.kvalidation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@Testable
fun testsConstraint() = rootContext<TestConstraint> {
    given {
        TestConstraint(prop1 = "Value 1", prop2 = null)
    }
    test("Message key is hwolf.validation.TestConstraint") {
        expectThat(this.messageKey).isEqualTo("hwolf.kvalidation.TestConstraint")
    }
}
