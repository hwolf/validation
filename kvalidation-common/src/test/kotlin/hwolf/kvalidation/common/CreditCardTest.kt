@file:Suppress("unused")

package hwolf.kvalidation.common

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.Validator
import hwolf.kvalidation.validator
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat

private data class CreditCardBean(val card: String)

private const val MASTERCARD = "5105105105105100"
private const val VISA = "4012888888881881"
private const val AMEX = "378282246310005"
private const val DISCOVERS = "6011111111111117"
private const val DINERS = "30569309025904"

@Testable
fun isCreditCard() = rootContext<Validator<CreditCardBean>> {
    given {
        validator { CreditCardBean::card { isCreditCard() } }
    }
    forEach(MASTERCARD, VISA, AMEX, DISCOVERS) { value ->
        test("$value is valid credit card") { validator ->
            val actual = validator.validator(CreditCardBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach("4417123456789112", "441712345678X112") { value ->
        test("$value is invalid credit card") { validator ->
            val actual = validator.validator(CreditCardBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "card",
                propertyType = "String",
                propertyValue = value,
                constraint = CreditorCard(setOf(
                    CreditorCard.Type.AMEX,
                    CreditorCard.Type.VISA,
                    CreditorCard.Type.MASTERCARD,
                    CreditorCard.Type.DISCOVER))))
        }
    }
}

@Testable
fun `is Mastercard`() = rootContext<Validator<CreditCardBean>> {
    given {
        validator { CreditCardBean::card { isCreditCard(CreditorCard.Type.MASTERCARD) } }
    }
    forEach(MASTERCARD) { value ->
        test("$value is valid Mastercard") { validator ->
            val actual = validator.validator(CreditCardBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(VISA, AMEX, DISCOVERS) { value ->
        test("$value is invalid Mastercard") { validator ->
            val actual = validator.validator(CreditCardBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "card",
                propertyType = "String",
                propertyValue = value,
                constraint = CreditorCard(setOf(CreditorCard.Type.MASTERCARD))))
        }
    }
}

@Testable
fun `is Diners card`() = rootContext<Validator<CreditCardBean>> {
    given {
        validator { CreditCardBean::card { isCreditCard(CreditorCard.Type.DINERS) } }
    }
    forEach(DINERS) { value ->
        test("$value is valid Diners card") { validator ->
            val actual = validator.validator(CreditCardBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(MASTERCARD, VISA, AMEX, DISCOVERS) { value ->
        test("$value is invalid Diners card") { validator ->
            val actual = validator.validator(CreditCardBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "card",
                propertyType = "String",
                propertyValue = value,
                constraint = CreditorCard(setOf(CreditorCard.Type.DINERS))))
        }
    }
}
