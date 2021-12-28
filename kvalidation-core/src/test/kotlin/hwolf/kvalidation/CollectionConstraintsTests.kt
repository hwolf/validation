@file:Suppress("unused")

package hwolf.kvalidation

import dev.minutest.given
import dev.minutest.rootContext
import dev.minutest.test
import org.junit.platform.commons.annotation.Testable
import strikt.api.expectThat

@Testable
fun isEmptyString() = rootContext<Validator<StringBean>> {
    given {
        validator { StringBean::string { isEmpty() } }
    }
    forEach("") { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(StringBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(" ", "xx") { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(StringBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "string",
                propertyType = PropertyType(String::class),
                propertyValue = value,
                constraint = Empty))
        }
    }
}

@Testable
fun isEmptyIterable() = rootContext<Validator<IterableBean>> {
    given {
        validator { IterableBean::range { isEmpty() } }
    }
    forEach(IntRange(1, 0)) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(IntRange(0, 1), IntRange(1, 1)) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "range",
                propertyType = PropertyType(Iterable::class),
                propertyValue = value,
                constraint = Empty))
        }
    }
}

@Testable
fun isEmptyCollection() = rootContext<Validator<ListBean>> {
    given {
        validator { ListBean::list { isEmpty() } }
    }
    forEach(emptyList<String>()) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(listOf("x1", "x2"), listOf("")) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "list",
                propertyType =PropertyType(List::class),
                propertyValue = value,
                constraint = Empty))
        }
    }
}

@Testable
fun isEmptyArray() = rootContext<Validator<ArrayBean>> {
    given {
        validator { ArrayBean::array { isEmpty() } }
    }
    forEach(emptyArray<String>()) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(arrayOf("x1", "x2"), arrayOf("")) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "array",
                propertyType = PropertyType(Array::class),
                propertyValue = value,
                constraint = Empty))
        }
    }
}

@Testable
fun isEmptyMap() = rootContext<Validator<MapBean>> {
    given {
        validator { MapBean::map { isEmpty() } }
    }
    forEach(emptyMap<String, Int>()) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(mapOf("x1" to 2, "x2" to 3), mapOf("" to 0)) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "map",
                propertyType = PropertyType(Map::class),
                propertyValue = value,
                constraint = Empty))
        }
    }
}

@Testable
fun isNotEmptyString() = rootContext<Validator<StringBean>> {
    given {
        validator { StringBean::string { isNotEmpty() } }
    }
    forEach(" ", "xx") { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(StringBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach("") { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(StringBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "string",
                propertyType = PropertyType(String::class),
                propertyValue = value,
                constraint = NotEmpty))
        }
    }
}

@Testable
fun isNotEmptyIterable() = rootContext<Validator<IterableBean>> {
    given {
        validator { IterableBean::range { isNotEmpty() } }
    }
    forEach(IntRange(0, 1), IntRange(1, 1)) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(IntRange(1, 0)) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "range",
                propertyType = PropertyType(Iterable::class),
                propertyValue = value,
                constraint = NotEmpty))
        }
    }
}

@Testable
fun isNotEmptyCollection() = rootContext<Validator<ListBean>> {
    given {
        validator { ListBean::list { isNotEmpty() } }
    }
    forEach(listOf("x1", "x2"), listOf("")) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(emptyList<String>()) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "list",
                propertyType = PropertyType(List::class),
                propertyValue = value,
                constraint = NotEmpty))
        }
    }
}

@Testable
fun isNotEmptyArray() = rootContext<Validator<ArrayBean>> {
    given {
        validator { ArrayBean::array { isNotEmpty() } }
    }
    forEach(arrayOf("x1", "x2"), arrayOf("")) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(emptyArray<String>()) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "array",
                propertyType = PropertyType(Array::class),
                propertyValue = value,
                constraint = NotEmpty))
        }
    }
}

@Testable
fun isNotEmptyMap() = rootContext<Validator<MapBean>> {
    given {
        validator { MapBean::map { isNotEmpty() } }
    }
    forEach(mapOf("x1" to 2, "x2" to 3), mapOf("" to 0)) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(emptyMap<String, Int>()) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "map",
                propertyType = PropertyType(Map::class),
                propertyValue = value,
                constraint = NotEmpty))
        }
    }
}

@Testable
fun hasSizeIterable() = rootContext<Validator<IterableBean>> {
    given {
        validator { IterableBean::range { hasSize(2, 4) } }
    }
    forEach(IntRange(0, 1), IntRange(0, 3)) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(IntRange(2, 2), IntRange(0, 4)) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(IterableBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "range",
                propertyType = PropertyType(Iterable::class),
                propertyValue = value,
                constraint = Size(2, 4)))
        }
    }
}

@Testable
fun hasSizeCollection() = rootContext<Validator<ListBean>> {
    given {
        validator { ListBean::list { hasSize(1, 4) } }
    }
    forEach(listOf("x"), listOf("1", "2", "3", "4")) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(emptyList(), listOf("1", "2", "3", "4", "5")) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(ListBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "list",
                propertyType = PropertyType(List::class),
                propertyValue = value,
                constraint = Size(1, 4)))
        }
    }
}

@Testable
fun hasSizeArray() = rootContext<Validator<ArrayBean>> {
    given {
        validator { ArrayBean::array { hasSize(1, 4) } }
    }
    forEach(arrayOf("x"), arrayOf("1", "2", "3", "4")) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(arrayOf(), arrayOf("1", "2", "3", "4", "5")) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(ArrayBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "array",
                propertyType = PropertyType(Array::class),
                propertyValue = value,
                constraint = Size(1, 4)))
        }
    }
}

@Testable
fun hasSizeMap() = rootContext<Validator<MapBean>> {
    given {
        validator { MapBean::map { hasSize(1, 3) } }
    }
    forEach(mapOf("x" to 1), mapOf("x1" to 3, "x2" to 5, "x3" to 1)) { value ->
        test("valid $value") { validator ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).isValid()
        }
    }
    forEach(emptyMap(), mapOf("x1" to 6, "x2" to 3, "x3" to 4, "x4" to 8)) { value ->
        test("invalid $value") { validator ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyName = "map",
                propertyType = PropertyType(Map::class),
                propertyValue = value,
                constraint = Size(1, 3)))
        }
    }
}
