@file:Suppress("unused")

package hwolf.kvalidation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat

@Testable
fun isLess() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isLess(23) } }
    }
    forEach(22) { value ->
        test("valid $value < 23") { validator ->
            val actual = validator.validator(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    forEach(23, 24) { value ->
        test("invalid $value < 23") { validator ->
            val actual = validator.validator(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = value,
                constraint = Less(23)))
        }
    }
}

@Testable
fun isLessWith() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isLess(TestBean::prop2) } }
    }
    forEach(TestBean(20, prop2 = 21), TestBean(20, prop2 = null)) { bean ->
        test("valid ${bean.prop1} < ${bean.prop2}") { validator ->
            val actual = validator.validator(bean)
            expectThat(actual).isValid()
        }
    }
    forEach(TestBean(23, prop2 = 23), TestBean(23, prop2 = 22)) { bean ->
        test("invalid ${bean.prop1} < ${bean.prop2}") { validator ->
            val actual = validator.validator(bean)
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = bean.prop1,
                constraint = LessWith("prop2")))
        }
    }
}

@Testable
fun isLessOrEqual() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isLessOrEqual(23) } }
    }
    forEach(22, 23) { value ->
        test("valid $value <= 23") { validator ->
            val actual = validator.validator(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    forEach(24) { value ->
        test("invalid $value <= 23") { validator ->
            val actual = validator.validator(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = value,
                constraint = LessOrEqual(23)))
        }
    }
}

@Testable
fun isLessOrEqualWith() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isLessOrEqual(TestBean::prop2) } }
    }
    forEach(TestBean(20, prop2 = 21), TestBean(23, prop2 = 23), TestBean(20, prop2 = null)) { bean ->
        test("valid ${bean.prop1} < ${bean.prop2}") { validator ->
            val actual = validator.validator(bean)
            expectThat(actual).isValid()
        }
    }
    forEach(TestBean(23, prop2 = 22)) { bean ->
        test("invalid ${bean.prop1} < ${bean.prop2}") { validator ->
            val actual = validator.validator(bean)
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = bean.prop1,
                constraint = LessOrEqualWith("prop2")))
        }
    }
}

@Testable
fun isGreater() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isGreater(4) } }
    }
    forEach(5) { value ->
        test("valid $value > 4") { validator ->
            val actual = validator.validator(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    forEach(3, 4) { value ->
        test("invalid $value > 4") { validator ->
            val actual = validator.validator(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = value,
                constraint = Greater(4)))
        }
    }
}

@Testable
fun isGreaterWith() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isGreater(TestBean::prop2) } }
    }
    forEach(TestBean(-4, prop2 = -5), TestBean(-4, prop2 = null)) { bean ->
        test("valid ${bean.prop1} > ${bean.prop2}") { validator ->
            val actual = validator.validator(bean)
            expectThat(actual).isValid()
        }
    }
    forEach(TestBean(-1, prop2 = -1), TestBean(4, prop2 = 5)) { bean ->
        test("invalid ${bean.prop1} > ${bean.prop2}") { validator ->
            val actual = validator.validator(bean)
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = bean.prop1,
                constraint = GreaterWith("prop2")))
        }
    }
}

@Testable
fun isGreaterOrEqual() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isGreaterOrEqual(4) } }
    }
    forEach(4, 5) { value ->
        test("valid $value >= 4") { validator ->
            val actual = validator.validator(TestBean(prop1 = value))
            expectThat(actual).isValid()
        }
    }
    forEach(3) { value ->
        test("invalid $value >= 4") { validator ->
            val actual = validator.validator(TestBean(prop1 = value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = value,
                constraint = GreaterOrEqual(4)))
        }
    }
}

@Testable
fun isGreaterOrEqualWith() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isGreaterOrEqual(TestBean::prop2) } }
    }
    forEach(TestBean(-1, prop2 = -1), TestBean(-4, prop2 = -5), TestBean(-4, prop2 = null)) { bean ->
        test("valid ${bean.prop1} >= ${bean.prop2}") { validator ->
            val actual = validator.validator(bean)
            expectThat(actual).isValid()
        }
    }
    forEach(TestBean(4, prop2 = 5)) { bean ->
        test("invalid ${bean.prop1} >= ${bean.prop2}") { validator ->
            val actual = validator.validator(bean)
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = bean.prop1,
                constraint = GreaterOrEqualWith("prop2")))
        }
    }
}

@Testable
fun isBetween() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isBetween(5, 7) } }
    }
    forEach(5, 6, 7) { value ->
        test("valid: $value is between (5, 7)") { validator ->
            val actual = validator.validator(TestBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(4, 8) { value ->
        test("invalid $value is in (5, 7)") { validator ->
            val actual = validator.validator(TestBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = value,
                constraint = Between(5, 7)))
        }
    }
}

@Testable
fun isNotBetween() = rootContext<Validator<TestBean>> {
    given {
        validator { TestBean::prop1 { isNotBetween(5, 7) } }
    }
    forEach(4, 8) { value ->
        test("valid: $value is not between (5, 7)") { validator ->
            val actual = validator.validator(TestBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(5, 6, 7) { value ->
        test("invalid $value is in (5, 7)") { validator ->
            val actual = validator.validator(TestBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "prop1",
                propertyType = "Int",
                propertyValue = value,
                constraint = NotBetween(5, 7)))
        }
    }
}
