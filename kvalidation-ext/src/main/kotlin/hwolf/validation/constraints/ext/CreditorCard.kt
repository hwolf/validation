package hwolf.validation.constraints.ext

import hwolf.validation.Constraint
import hwolf.validation.ConstraintBuilder
import org.apache.commons.validator.routines.CreditCardValidator

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

fun ConstraintBuilder<String>.isCreditCard() =
    isCreditCard(CreditorCard.Type.AMEX,
        CreditorCard.Type.VISA,
        CreditorCard.Type.MASTERCARD,
        CreditorCard.Type.DISCOVER)

fun ConstraintBuilder<String>.isCreditCard(vararg cardTypes: CreditorCard.Type) =
    isCreditCard(cardTypes.toSet())

fun ConstraintBuilder<String>.isCreditCard(cardTypes: Collection<CreditorCard.Type>) =
    validate(CreditorCard(cardTypes)) {
        CreditCardValidator(cardTypes.sumOf(CreditorCard.Type::x)).isValid(it)
    }
