package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class NotBlankTests : FunSpec({

    val validator = validator<StringBean> { StringBean::string { isNotBlank() } }

    context("is not blank") {
        withData(nameFn = { "'${it}'" },
            "a", " x  ") { value ->
            val actual = validator.validate(StringBean(string = value))
            expectThat(actual).isValid()
        }
    }
    context("is blank") {
        withData(nameFn = { "'${it}'" },
            "", "   ") { value ->
            val actual = validator.validate(StringBean(string = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("string")),
                propertyType = PropertyType("String"),
                propertyValue = value,
                constraint = NotBlank))
        }
    }
})
