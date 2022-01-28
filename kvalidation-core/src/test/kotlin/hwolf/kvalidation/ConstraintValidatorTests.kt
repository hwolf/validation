package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ConstraintValidatorTests : FunSpec({

    fun constraintValidator(test: (String, Any) -> Boolean) =
        ConstraintValidator<String, String>(TestConstraint(), test)


    test("test returns true") {
        val context = mockk<ValidationContext<String>>(relaxed = true)
        every { context.bean } returns "xx"
        constraintValidator { _, _ -> true }.invoke("xx", context)
        verify(exactly = 0) {
            context.constraintViolation(any(), any())
        }
    }
    test("test returns false") {
        val context = mockk<ValidationContext<String>>(relaxed = true)
        constraintValidator { _, _ -> false }.invoke("xx", context)
        verify {
            context.constraintViolation(TestConstraint(), "xx")
        }
    }
})
