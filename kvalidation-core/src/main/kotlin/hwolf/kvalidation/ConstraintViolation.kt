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

/**
 * Represents a constraint violation
 *
 * @constructor creates a constraint violation
 * @property propertyPath the path of the property that violated the constraint
 * @property propertyType the type of the property that violated the constraint
 * @property propertyValue the invalid value
 * @property constraint the violated constraint
 */
data class ConstraintViolation(
    val propertyPath: List<PropertyName>,
    val propertyType: PropertyType?,
    val propertyValue: Any?,
    val constraint: Constraint
) {
    val propertyName = propertyPath.lastOrNull()
}

data class PropertyName(
    val name: String,
    val key: Any? = null
) {
    override fun toString() = when (key == null) {
        true -> name
        else -> "$name[$key]"
    }
}

data class PropertyType(
    val type: String
) {
    override fun toString() = type
}
