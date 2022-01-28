package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class EmptyStringTests : FunSpec({

    val validator = validator<StringBean> { StringBean::string { isEmpty() } }

    context("is empty") {
        withData(nameFn = { "'$it'" }, listOf("")) { value ->
            val actual = validator.validate(StringBean(value))
            expectThat(actual).isValid()
        }
    }
    context("is not empty") {
        withData(nameFn = { "'$it'" }, " ", "xx") { value ->
            val actual = validator.validate(StringBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("string")),
                propertyType = PropertyType("String"),
                propertyValue = value,
                constraint = Empty))
        }
    }
})