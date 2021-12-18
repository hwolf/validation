package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ValidationBuilder
import hwolf.kvalidation.validate
import org.apache.commons.validator.routines.EmailValidator

/** A constraint that validate if the value is an email. */
data class Email(
    val options: Collection<Options>
) : Constraint {
    enum class Options {

        /** Should local addresses be considered valid ? */
        AllowLocal
    }
}

/** Validates if the property value is an email. */
fun <T> ValidationBuilder<T, String>.isEmail() = isEmail(emptySet())

/** Validates if the property value is an email. */
fun <T> ValidationBuilder<T, String>.isEmail(vararg options: Email.Options) = isEmail(options.toSet())

/** Validates if the property value is an email. */
fun <T> ValidationBuilder<T, String>.isEmail(options: Collection<Email.Options>) =
    validate(Email(options)) { v, _ ->
        EmailValidator.getInstance(Email.Options.AllowLocal in options, false).isValid(v)
    }
