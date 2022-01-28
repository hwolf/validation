package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class HasLengthTests : FunSpec({

    val validator = validator<StringBean> { StringBean::string { hasLength(3, 5) } }

    context("has length") {
        withData("123", "12345") { value ->
            val actual = validator.validate(StringBean(string = value))
            expectThat(actual).isValid()
        }
    }
    context("has not length") {
        withData("12", "123456") { value ->
            val actual = validator.validate(StringBean(string = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("string")),
                propertyType = PropertyType("String"),
                propertyValue = value,
                constraint = Length(3, 5)))
        }
    }
})