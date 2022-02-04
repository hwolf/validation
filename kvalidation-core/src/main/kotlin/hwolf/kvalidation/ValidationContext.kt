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

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KType

class ValidationContext<T> private constructor(
    val bean: T,
    private val errs: MutableList<ConstraintViolation>,
    private val propertyPath: List<PropertyName>,
    private val propertyType: PropertyType?
) {
    constructor(bean: T) : this(bean, mutableListOf(), emptyList(), null)

    val errors get() = errs.toList()

    fun constraintViolation(constraint: Constraint, propertyValue: Any?) {
        errs.add(ConstraintViolation(
            propertyPath = propertyPath,
            propertyType = propertyType,
            propertyValue = propertyValue,
            constraint = constraint
        ))
    }

    internal fun <V, X> withProperty(property: KProperty1<V, X>, value: V) = ValidationContext(
        bean = value,
        errs = errs,
        propertyPath = buildPropertyName(property),
        propertyType = buildTypeName(findClass(property.returnType)))

    internal fun <V, X> withProperty(property: KProperty1<V, X>, collection: V, key: Any, value: Any?) =
        withProperty(property, collection, key, when (value) {
            null -> null
            else -> value::class
        })

    private fun <V, X> withProperty(property: KProperty1<V, X>, value: V, key: Any, valueClass: KClass<*>?) =
        ValidationContext(
            bean = value,
            errs = errs,
            propertyPath = buildPropertyName(property, key),
            propertyType = buildTypeName(valueClass))

    internal fun <V> withRoot(root: V): ValidationContext<V> = ValidationContext(
        bean = root,
        errs = errs,
        propertyPath = propertyPath,
        propertyType = propertyType)

    private fun buildPropertyName(property: KProperty1<*, *>, key: Any? = null) =
        propertyPath + PropertyName(property.name, key)

    private fun buildTypeName(klass: KClass<*>?) = klass?.let { t -> t.simpleName?.let { n -> PropertyType(n) } }

    private fun findClass(type: KType) = type.classifier as? KClass<*>
}

