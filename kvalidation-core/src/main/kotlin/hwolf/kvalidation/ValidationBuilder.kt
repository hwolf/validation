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

import kotlin.experimental.ExperimentalTypeInference
import kotlin.reflect.KProperty1

typealias ValidationAction<V, U> = ValidationBuilder<V, U>.() -> Unit

/** Builds a [Validator] for the type [V]. */
@OptIn(ExperimentalTypeInference::class)
fun <V> validator(init: ValidationAction<V, V>): Validator<V> {
    val validators = buildValidators(init)
    return { value, context -> runValidators(validators, value, context) }
}

@DslMarker
annotation class Marker

@Marker
class ValidationBuilder<T, V> {

    private val validators = mutableListOf<PropertyValidator<V, T>>()

    infix operator fun <U : Any> KProperty1<V, U>.invoke(init: ValidationAction<V, U>) {
        val subValidators = buildValidators(init)
        validators += { value: V, context: ValidationContext<T> ->
            runValidators(subValidators, this(value), context.withProperty(this, value))
        }
    }

    infix fun <U : Any> KProperty1<V, U?>.required(init: ValidationAction<V, U>) {
        val subValidators = buildValidators(init)
        validators += { value, context ->
            when (val v = this(value)) {
                null -> context.withProperty(this, value).constraintViolation(Required, null)
                else -> runValidators(subValidators, v, context.withProperty(this, value))
            }
        }
    }

    infix fun <U : Any> KProperty1<V, U?>.ifPresent(init: ValidationAction<V, U>) {
        val subValidators = buildValidators(init)
        validators += { value, context ->
            this(value)?.let { v ->
                runValidators(subValidators, v, context.withProperty(this, value))
            }
        }
    }

    @JvmName("eachIterable")
    infix fun <U> KProperty1<V, Iterable<U>>.each(init: ValidationAction<V, U>) {
        val subValidators = buildValidators(init)
        validators += { value, context ->
            this(value).forEachIndexed { i, v ->
                runValidators(subValidators, v, context.withProperty(this, value, i, v))
            }
        }
    }

    @JvmName("eachArray")
    infix fun <U> KProperty1<V, Array<U>>.each(init: ValidationAction<V, U>) {
        val subValidators = buildValidators(init)
        validators += { value, context ->
            this(value).forEachIndexed { i, v ->
                runValidators(subValidators, v, context.withProperty(this, value, i, v))
            }
        }
    }

    @JvmName("eachMap")
    infix fun <K : Any, U> KProperty1<V, Map<K, U>>.each(init: ValidationAction<V, U>) {
        val subValidators: List<PropertyValidator<U, V>> = buildValidators(init)
        validators += { value, context ->
            this(value).forEach { (k, v) ->
                runValidators(subValidators, v, context.withProperty(this, value, k, v))
            }
        }
    }

    fun addValidator(validator: PropertyValidator<V, T>) {
        validators += validator
    }

    internal fun validators() = validators.toList()
}

fun <T, V> ValidationBuilder<T, V>.onlyIf(predicate: V.() -> Boolean, init: ValidationBuilder<T, V>.() -> Unit) {
    val subValidators = buildValidators(init)
    addValidator { value, context ->
        if (predicate(value)) {
            runValidators(subValidators, value, context)
        }
    }
}

infix fun <T, V> ValidationBuilder<T, V>.use(validator: Validator<V>) {
    addValidator { value, context ->
        validator(value, context.withRoot(value))
    }
}

private fun <T, V> buildValidators(init: ValidationAction<V, T>) =
    ValidationBuilder<V, T>().apply(init).validators()

private fun <V, T> runValidators(validators: List<PropertyValidator<V, T>>, value: V, context: ValidationContext<T>) =
    validators.forEach { it(value, context) }

fun <T, V> ValidationBuilder<T, V>.validate(constraint: Constraint, test: (V, T) -> Boolean) =
    addValidator(ConstraintValidator(constraint, test))
