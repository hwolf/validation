/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hwolf.kvalidation.common

import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.PropertyName
import hwolf.kvalidation.PropertyType
import hwolf.kvalidation.validate
import hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.property.Arb
import io.kotest.property.arbitrary.domain
import io.kotest.property.arbitrary.email
import io.kotest.property.arbitrary.emailLocalPart
import io.kotest.property.arbitrary.map
import io.kotest.property.checkAll
import strikt.api.expectThat

class EmailTests : FunSpec({

    data class EmailBean(val email: String)

    val normalMail = "max.mustermann@muster.de"
    val localMail = "max.mustermann@localhost"

    context("Validate mail") {

        val validator = validator<EmailBean> { EmailBean::email { isEmail() } }

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
        context("random mail is valid") {
            checkAll(Arb.email()) { mail ->
                val actual = validator.validate(EmailBean(mail))
                expectThat(actual).isValid()
            }
        }
        context("random local mail is invalid") {
            checkAll(Arb.emailLocalPart()) { mail ->
                val actual = validator.validate(EmailBean(mail))
                expectThat(actual).not().isValid()
            }
        }
    }

    context("validate local mails") {

        val validator = validator<EmailBean> { EmailBean::email { isEmail(Email.Options.AllowLocal) } }

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
        context("random mail is valid") {
            checkAll(Arb.email()) { mail ->
                val actual = validator.validate(EmailBean(mail))
                expectThat(actual).isValid()
            }
        }
        context("random local mail is valid") {
            checkAll(Arb.email(domainGen = Arb.serverName())) { mail ->
                val actual = validator.validate(EmailBean(mail))
                expectThat(actual).isValid()
            }
        }
    }
})

private fun Arb.Companion.serverName() = domain().map { it.substring(0, it.indexOf('.')) }