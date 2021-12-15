package hwolf.kvalidation.common

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ConstraintBuilder
import org.apache.commons.validator.routines.EmailValidator

data class Email(
    val options: Collection<Options>
) : Constraint {
    enum class Options {
        /** Should local addresses be considered valid ? */
        AllowLocal
    }
}

fun ConstraintBuilder<String>.isEmail() = isEmail(emptySet())

fun ConstraintBuilder<String>.isEmail(vararg options: Email.Options) = isEmail(options.toSet())

fun ConstraintBuilder<String>.isEmail(options: Collection<Email.Options>) = validate(Email(options)) {
    EmailValidator.getInstance(Email.Options.AllowLocal in options, false).isValid(it)
}
