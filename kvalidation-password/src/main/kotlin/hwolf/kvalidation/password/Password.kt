package hwolf.kvalidation.password

import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ValidationBuilder
import hwolf.kvalidation.validate
import org.passay.PasswordData
import org.passay.PasswordValidator
import org.passay.Rule

data class Password(
    val rules: List<Rule>,
    val passwordReferences: List<PasswordData.Reference>,
    val origin: PasswordData.Origin
) : Constraint {
    override val parameters = mapOf("rules" to rules, "origin" to origin)
}

fun <T> ValidationBuilder<T, String>.password(
    rules: List<Rule>,
    passwordReferences: List<PasswordData.Reference> = emptyList(),
    origin: PasswordData.Origin = PasswordData.Origin.User
) = validate(Password(rules, passwordReferences, origin), true) { v, _ ->
    PasswordValidator(rules).validate(PasswordData().apply {
        this.password = v
        this.origin = origin
        this.passwordReferences = passwordReferences
    }).isValid
}
