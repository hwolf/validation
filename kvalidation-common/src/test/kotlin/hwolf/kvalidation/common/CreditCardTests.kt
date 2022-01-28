package hwolf.kvalidation.common

import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.PropertyName
import hwolf.kvalidation.PropertyType
import hwolf.kvalidation.validate
import hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class CreditCardTests : FunSpec({

    val mastercard = "5105105105105100"
    val visa = "4012888888881881"
    val amex = "378282246310005"
    val discovers = "6011111111111117"
    val diners = "30569309025904"

    data class CreditCardBean(val card: String)

    context("validate credit card") {

        val validator = validator<CreditCardBean> { CreditCardBean::card { isCreditCard() } }

        context("is valid credit card") {
            withData(mastercard, visa, amex, discovers) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).isValid()
            }
        }
        context("is invalid credit card") {

            withData("4417123456789112", "441712345678X112") { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).hasViolations(ConstraintViolation(
                    propertyPath = listOf(PropertyName("card")),
                    propertyType = PropertyType("String"),
                    propertyValue = value,
                    constraint = CreditorCard(setOf(
                        CreditorCard.Type.AMEX,
                        CreditorCard.Type.VISA,
                        CreditorCard.Type.MASTERCARD,
                        CreditorCard.Type.DISCOVER))))
            }
        }
    }

    context("Validate mastercard") {

        val validator =
            validator<CreditCardBean> { CreditCardBean::card { isCreditCard(CreditorCard.Type.MASTERCARD) } }

        context("is valid mastercard") {
            withData(listOf(mastercard)) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).isValid()
            }
        }
        context("is invalid mastercard") {
            withData(visa, amex, discovers) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).hasViolations(ConstraintViolation(
                    propertyPath = listOf(PropertyName("card")),
                    propertyType = PropertyType("String"),
                    propertyValue = value,
                    constraint = CreditorCard(setOf(CreditorCard.Type.MASTERCARD))))
            }
        }
    }

    context("Validate diners card") {

        val validator = validator<CreditCardBean> { CreditCardBean::card { isCreditCard(CreditorCard.Type.DINERS) } }

        context("is valid diners card") {
            withData(listOf(diners)) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).isValid()
            }
        }
        context("is invalid diners card") {
            withData(mastercard, visa, amex, discovers) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).hasViolations(ConstraintViolation(
                    propertyPath = listOf(PropertyName("card")),
                    propertyType = PropertyType("String"),
                    propertyValue = value,
                    constraint = CreditorCard(setOf(CreditorCard.Type.DINERS))))
            }
        }
    }
})
