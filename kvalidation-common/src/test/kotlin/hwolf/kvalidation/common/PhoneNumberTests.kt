package hwolf.kvalidation.common

import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.ValidationContext
import hwolf.kvalidation.Validator
import hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isNotEmpty

class PhoneNumberTests : FunSpec({

    data class TestBean(val phoneNumber: String)

    fun doTest(phoneNumber: String, validator: Validator<TestBean>): List<ConstraintViolation> {
        val bean = TestBean(phoneNumber)
        val context = ValidationContext(bean)
        validator(bean, context)
        return context.errors
    }

    context("Possible phone numbers with default region Germany") {

        val validator = validator<TestBean> { TestBean::phoneNumber { isPhoneNumber("DE") } }

        withData(
            "06221383250",
            "06221 383250",
            "06221/383250",
            "00 49 6221 383250",
            "+49 6221 383250",
            "+61403123456",
            "475465XXXXX",
            "00000"
        ) {
            expectThat(doTest(it, validator)).isEmpty()
        }
    }

    context("Invalid phone numbers") {

        val validator = validator<TestBean> { TestBean::phoneNumber { isPhoneNumber("DE") } }

        withData(
            "Pli plo plu",
            "00 49 6221 383((()=/()//(&250"
        ) {
            expectThat(doTest(it, validator)).isNotEmpty()
        }
    }

    context("Only possible phone numbers for Germany") {

        val validator = validator<TestBean> {
            TestBean::phoneNumber {
                isPhoneNumber("DE", PhoneNumber.Options.OnlyForRegion)
            }
        }

        withData(
            "06221383250",
            "06221 383250",
            "06221/383250",
            "00 49 6221 383250",
            "+49 6221 383250",
            "00000"
        ) {
            expectThat(doTest(it, validator)).isEmpty()
        }
    }

    context("Valid phone numbers with default region Germany") {

        val validator = validator<TestBean> {
            TestBean::phoneNumber {
                isPhoneNumber("DE", PhoneNumber.Options.Valid)
            }
        }

        withData(
            "06221383250",
            "06221 383250",
            "06221/383250",
            "00 49 6221 383250",
            "+49 6221 383250",
            "+61403123456",
            "475465XXXXX"
        ) {
            expectThat(doTest(it, validator)).isEmpty()
        }
    }

    context("Only valid phone numbers for Germany") {

        val validator = validator<TestBean> {
            TestBean::phoneNumber {
                isPhoneNumber("DE", PhoneNumber.Options.Valid, PhoneNumber.Options.OnlyForRegion)
            }
        }

        withData(
            "06221383250",
            "06221 383250",
            "06221/383250",
            "00 49 6221 383250",
            "+49 6221 383250",
            "475465XXXXX"
        ) {
            expectThat(doTest(it, validator)).isEmpty()
        }
    }
})
