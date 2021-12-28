@file:Suppress("unused")

package hwolf.kvalidation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat

@Testable
fun isEqual() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isEqual(2) } }
    }
    forEach(2) { value ->
        test("valid: $value == 2") {
            val actual = validate(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    forEach(0, 3) { value ->
        test("invalid: $value == 2") {
            val actual = validate(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = PropertyType(Int::class),
                propertyValue = value,
                constraint = Equal(2)))
        }
    }
}

@Testable
fun isEqualWith() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isEqual(TestBean::prop2) } }
    }
    forEach(Pair(3, 3), Pair(2, null)) { (v1, v2) ->
        test("valid: $v1 == $2") {
            val actual = validate(TestBean(prop1 = v1, prop2 = v2))
            expectThat(actual).isValid()
        }
    }
    forEach(Pair(0, 1), Pair(2, 1)) { (v1, v2) ->
        test("invalid: $v1 == $v2") {
            val actual = validate(TestBean(prop1 = v1, prop2 = v2))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = PropertyType(Int::class),
                propertyValue = v1,
                constraint = EqualWith("prop2")))
        }
    }
}

@Testable
fun isIn() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isIn(1, 3) } }
    }
    forEach(1, 3) { value ->
        test("valid: $value in (1, 3)") {
            val actual = validate(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    forEach(0, 2, 4) { value ->
        test("invalid: $value in (1, 3)") {
            val actual = validate(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = PropertyType(Int::class),
                propertyValue = value,
                constraint = In(listOf(1, 3))))
        }
    }
}

@Testable
fun isNotIn() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isNotIn(1, 3) } }
    }
    forEach(0, 2, 4) { value ->
        test("valid: $value not in (1, 3)") {
            val actual = validate(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    forEach(1, 3) { value ->
        test("invalid: $value not in (1, 3)") {
            val actual = validate(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = PropertyType(Int::class),
                propertyValue = value,
                constraint = NotIn(listOf(1, 3))))
        }
    }
}
