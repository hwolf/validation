@file:Suppress("unused")

package hwolf.kvalidation.common

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.PropertyType
import hwolf.kvalidation.Validator
import hwolf.kvalidation.validate
import hwolf.kvalidation.validator
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat

private data class EmailBean(val email: String)

private const val NORMAL_MAIL = "max.mustermann@muster.de"
private const val LOCAL_MAIL = "max.mustermann@localhost"

@Testable
fun isEmail() = rootContext<Validator<EmailBean>> {
    given {
        validator { EmailBean::email { isEmail() } }
    }
    forEach(NORMAL_MAIL) { mail ->
        test("$mail is valid") { validator ->
            val actual = validator.validate(EmailBean(mail))
            expectThat(actual).isValid()
        }
    }
    forEach(LOCAL_MAIL, "max.mustermann@", "muster.xy") { mail ->
        test("$mail is invalid") { validator ->
            val actual = validator.validate(EmailBean(mail))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "email",
                propertyType = PropertyType("String"),
                propertyValue = mail,
                constraint = Email(emptySet())))
        }
    }
}

@Testable
fun isLocalEmail() = rootContext<Validator<EmailBean>> {
    given {
        validator { EmailBean::email { isEmail(Email.Options.AllowLocal) } }
    }
    forEach(NORMAL_MAIL, LOCAL_MAIL) { mail ->
        test("$mail is valid") { validator ->
            val actual = validator.validate(EmailBean(mail))
            expectThat(actual).isValid()
        }
    }
    forEach("max.mustermann@", "muster.xy") { mail ->
        test("$mail is invalid") { validator ->
            val actual = validator.validate(EmailBean(mail))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "email",
                propertyType = PropertyType("String"),
                propertyValue = mail,
                constraint = Email(setOf(Email.Options.AllowLocal))))
        }
    }
}
