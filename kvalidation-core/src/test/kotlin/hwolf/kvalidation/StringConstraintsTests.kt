@file:Suppress("unused")

package hwolf.kvalidation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat

@Testable
fun isNotBlank() = rootContext<Validator<StringBean>> {
    given {
        validator { StringBean::string { isNotBlank() } }
    }
    forEach("x", " x  ") { value ->
        test("valid: '$value' is not blank") {
            val actual = validate(StringBean(string = value))
            expectThat(actual).isValid()
        }
    }
    forEach("", "   ") { value ->
        test("invalid: '$value' is not blank") {
            val actual = validate(StringBean(string = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "string",
                propertyType = "String",
                propertyValue = value,
                constraint = NotBlank))
        }
    }
}

@Testable
fun hasLength() = rootContext<Validator<StringBean>> {
    given {
        validator { StringBean::string { hasLength(3, 5) } }
    }
    forEach("123", "12345") { value ->
        test("valid: '$value' is not blank") {
            val actual = validate(StringBean(string = value))
            expectThat(actual).isValid()
        }
    }
    forEach("12", "123456") { value ->
        test("invalid: '$value' is not blank") {
            val actual = validate(StringBean(string = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "string",
                propertyType = "String",
                propertyValue = value,
                constraint = Length(3, 5)))
        }
    }
}
