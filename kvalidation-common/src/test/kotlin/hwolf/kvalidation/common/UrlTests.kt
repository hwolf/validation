package hwolf.kvalidation.common

import hwolf.kvalidation.ValidationContext
import hwolf.kvalidation.validator
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty

class UrlTests : FunSpec({

    data class TestBean(val url: String)

    val validator = validator<TestBean> { TestBean::url { isUrl() } }

    context("valid URLs") {
        withData(listOf(TestBean("https://www.google.com"))) {
            val context = ValidationContext(it)
            validator(it, context)
            expectThat(context.errors).isEmpty()
        }
    }
    context("invalid URLs") {
        withData(TestBean("aaa."), TestBean("go.1aa")) {
            val context = ValidationContext(it)
            validator(it, context)
            expectThat(context.errors).hasSize(1)
        }
    }
})
