package hwolf.kvalidation.common

import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.PropertyName
import hwolf.kvalidation.PropertyType
import hwolf.kvalidation.validate
import hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class EmailTests : FunSpec({

    data class EmailBean(val email: String)

    val normalMail = "max.mustermann@muster.de"
    val localMail = "max.mustermann@localhost"

    context("Validate mail") {

        val validator = validator { EmailBean::email { isEmail() } }

        context("is valid mail") {
            withData(listOf(normalMail)) { mail ->
                val actual = validator.validate(EmailBean(mail))
                expectThat(actual).isValid()
            }
        }
        context("is invalid mail") {
            withData(localMail, "max.mustermann@", "muster.xy") { mail ->
                val actual = validator.validate(EmailBean(mail))
                expectThat(actual).hasViolations(ConstraintViolation(
                    propertyPath = listOf(PropertyName("email")),
                    propertyType = PropertyType("String"),
                    propertyValue = mail,
                    constraint = Email(emptySet())))
            }
        }
    }

    context("validate local mails") {

        val validator = validator { EmailBean::email { isEmail(Email.Options.AllowLocal) } }

        context("is valid local mail") {
            withData(normalMail, localMail) { mail ->
                val actual = validator.validate(EmailBean(mail))
                expectThat(actual).isValid()
            }
        }

        context("is invalid local mail") {
            withData("max.mustermann@", "muster.xy") { mail ->
                val actual = validator.validate(EmailBean(mail))
                expectThat(actual).hasViolations(ConstraintViolation(
                    propertyPath = listOf(PropertyName("email")),
                    propertyType = PropertyType("String"),
                    propertyValue = mail,
                    constraint = Email(setOf(Email.Options.AllowLocal))))
            }
        }
    }
})
