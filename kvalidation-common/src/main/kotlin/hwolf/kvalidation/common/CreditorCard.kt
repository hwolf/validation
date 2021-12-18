package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ValidationBuilder
import hwolf.kvalidation.validate
import org.apache.commons.validator.routines.CreditCardValidator

/** A constraint that validate if the value is a credit card number. */
data class CreditorCard(
    val cardTypes: Collection<Type>
) : Constraint {

    enum class Type(
        internal val x: Long
    ) {
        /** Option specifying that American Express cards are allowed. */
        AMEX(CreditCardValidator.AMEX),

        /** Option specifying that Visa cards are allowed. */
        VISA(CreditCardValidator.VISA),

        /** Option specifying that Mastercard cards are allowed. */
        MASTERCARD(CreditCardValidator.MASTERCARD),

        /** Option specifying that Discover cards are allowed. */
        DISCOVER(CreditCardValidator.DISCOVER),

        /** Option specifying that Diners cards are allowed. */
        DINERS(CreditCardValidator.DINERS),

        /** Option specifying that VPay (Visa) cards are allowed. */
        VPAY(CreditCardValidator.VPAY)
    }
}

/** Validates if the property value is a credit card number. */
fun <T> ValidationBuilder<T, String>.isCreditCard() =
    isCreditCard(CreditorCard.Type.AMEX,
        CreditorCard.Type.VISA,
        CreditorCard.Type.MASTERCARD,
        CreditorCard.Type.DISCOVER)

/** Validates if the property value is a credit card number. */
fun <T> ValidationBuilder<T, String>.isCreditCard(vararg cardTypes: CreditorCard.Type) =
    isCreditCard(cardTypes.toSet())

/** Validates if the property value is a credit card number. */
fun <T> ValidationBuilder<T, String>.isCreditCard(cardTypes: Collection<CreditorCard.Type>) =
    validate(CreditorCard(cardTypes)) { v, _ ->
        CreditCardValidator(cardTypes.sumOf(CreditorCard.Type::x)).isValid(v)
    }
