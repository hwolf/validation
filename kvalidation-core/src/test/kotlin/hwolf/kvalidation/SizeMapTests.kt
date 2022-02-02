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
package hwolf.kvalidation

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import strikt.api.expectThat

class SizeMapTests : FunSpec({

    val validator = validator<MapBean> { MapBean::map { hasSize(1, 3) } }

    context("has size") {
        withData(mapOf("x" to 1), mapOf("x1" to 3, "x2" to 5, "x3" to 1)) { value ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).isValid()
        }
    }
    context("has not size") {
        withData(emptyMap(), mapOf("x1" to 6, "x2" to 3, "x3" to 4, "x4" to 8)) { value ->
            val actual = validator.validate(MapBean(value))
            expectThat(actual).hasViolations(ConstraintViolation(
                propertyPath = listOf(PropertyName("map")),
                propertyType = PropertyType("Map"),
                propertyValue = value,
                constraint = Size(1, 3)))
        }
    }
})
