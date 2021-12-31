package hwolf.kvalidation

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.map

class ValidationBuilderTest {

    class TestBean(val prop: String, val prop2: String? = null)

    @Test
    fun `Set message key - test 1`() {
        val validator = validator<TestBean> {
            TestBean::prop { isEqual("xx") messageKey "message-key" }
        }
        val actual = validator.validate(TestBean("xy"))
        expectThat(actual.violations).map { it.constraint.messageKey }.contains("message-key")
    }

    @Test
    fun `Set message key - test 2`() {
        val validator = validator<TestBean> {
            TestBean::prop { isEqual(TestBean::prop2) messageKey "message-key-x" }
        }
        val actual = validator.validate(TestBean("xy", prop2 = "yx"))
        expectThat(actual.violations).map { it.constraint.messageKey }.contains("message-key-x")
    }

    @Test
    fun use() {
        val parent = validator<TestBean> {
            TestBean::prop { isEqual("yx") }
        }
        val validator = validator<TestBean> {
            use(parent)
        }
        val actual = validator.validate(TestBean("xy", prop2 = "yx"))
        expectThat(actual).hasViolations(
            ConstraintViolation(
                propertyName = "prop",
                propertyType = PropertyType("String"),
                propertyValue = "xy",
                constraint = Equal(value = "yx")))
    }
}
