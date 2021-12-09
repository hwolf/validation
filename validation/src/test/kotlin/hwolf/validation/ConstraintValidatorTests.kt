package hwolf.validation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import io.mockk.*
import org.junit.platform.commons.annotation.Testable

@Testable
fun constraintValidator() = rootContext<ValidationContext<String>> {

    fun constraintValidator(test: (String, Any) -> Boolean) =
        ConstraintValidator<String, String>(TestConstraint(), test)

    given {
        mockk(relaxed = true)
    }
    test("test returns true") { context ->
        every { context.bean } returns "xx"
        constraintValidator { _, _ -> true }.invoke("xx", context)
        verify(exactly = 0) {
            context.constraintViolation(any(), any())
        }
    }
    test("test returns false") { context ->
        constraintValidator { _, _ -> false }.invoke("xx", context)
        verify {
            context.constraintViolation(TestConstraint(), "xx")
        }
    }
}
