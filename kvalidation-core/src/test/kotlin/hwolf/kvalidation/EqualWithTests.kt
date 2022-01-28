package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class EqualWithTests : FunSpec({

    val validator = validator<TestBean> { TestBean::prop1 { isEqual(TestBean::prop2) } }

    context("is equal") {
        withData(Pair(3, 3), Pair(2, null)) { (v1, v2) ->
            val actual = validator.validate(TestBean(prop1 = v1, prop2 = v2))
            expectThat(actual).isValid()
        }
    }
    context("is not equal") {
        withData(Pair(0, 1), Pair(2, 1)) { (v1, v2) ->
            val actual = validator.validate(TestBean(prop1 = v1, prop2 = v2))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("prop1")),
                propertyType = PropertyType("Int"),
                propertyValue = v1,
                constraint = EqualWith("prop2")))
        }
    }
})