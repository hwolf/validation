package hwolf.kvalidation.common

import hwolf.kvalidation.ValidationContext
import hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty

class IbanTests : FunSpec({

    data class TestBean(val iban: String)

    val validator = validator { TestBean::iban { isIban() } }

    context("valid IBANs") {
        withData(TestBean("AT611904300234573201"), TestBean("DE89370400440532013000")) {
            val context = ValidationContext(it)
            validator(it, context)
            expectThat(context.errors).isEmpty()
        }
    }
    context("invalid IBANs") {
        withData(TestBean("At611904300234573201"), TestBean("DE99370400440532013000")) {
            val context = ValidationContext(it)
            validator(it, context)
            expectThat(context.errors).hasSize(1)
        }
    }
})
