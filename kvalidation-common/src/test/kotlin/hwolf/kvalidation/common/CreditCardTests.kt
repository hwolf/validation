package hwolf.kvalidation.common

import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.PropertyName
import hwolf.kvalidation.PropertyType
import hwolf.kvalidation.validate
import hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

private data class CreditCardBean(val card: String)

private const val MASTERCARD = "5105105105105100"
private const val VISA = "4012888888881881"
private const val AMEX = "378282246310005"
private const val DISCOVERS = "6011111111111117"
private const val DINERS = "30569309025904"

class CreditCardTests : FunSpec({

    context("validate credit card") {

        val validator = validator { CreditCardBean::card { isCreditCard() } }

        context("is valid credit card") {
            withData(MASTERCARD, VISA, AMEX, DISCOVERS) { value ->
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

        val validator = validator { CreditCardBean::card { isCreditCard(CreditorCard.Type.MASTERCARD) } }

        context("is valid mastercard") {
            withData(listOf(MASTERCARD)) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).isValid()
            }
        }
        context("is invalid mastercard") {
            withData(VISA, AMEX, DISCOVERS) { value ->
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

        val validator = validator { CreditCardBean::card { isCreditCard(CreditorCard.Type.DINERS) } }

        context("is valid diners card") {
            withData(listOf(DINERS)) { value ->
                val actual = validator.validate(CreditCardBean(value))
                expectThat(actual).isValid()
            }
        }
        context("is invalid diners card") {
            withData(MASTERCARD, VISA, AMEX, DISCOVERS) { value ->
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
