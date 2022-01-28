package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class NotEmptyStringTests : FunSpec({

    val validator = validator<StringBean> { StringBean::string { isNotEmpty() } }

    context("is not empty") {
        withData(nameFn = { "'$it'" }, " ", "xx") { value ->
            val actual = validator.validate(StringBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is empty") {
        withData(nameFn = { "'$it'" }, listOf("")) { value ->
            val actual = validator.validate(StringBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("string")),
                propertyType = PropertyType("String"),
                propertyValue = value,
                constraint = NotEmpty))
        }
    }
})