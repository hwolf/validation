package hwolf.validation

import kotlin.reflect.KProperty1

typealias ValidationAction<V, U> = ValidationBuilder<V, U>.() -> Unit

/** Builds a [Validator] for the type [V]. */
fun <V> validator(init: ValidationAction<V, V>): Validator<V> {
    val validators = buildValidators(init)
    return { value, context -> runValidators(validators, value, context) }
}

interface ConstraintBuilder<V> {
    fun validate(constraint: Constraint, test: (V) -> Boolean): ConstraintHelper
}

interface ExtConstraintBuilder<T, V> {
    fun validate(constraint: Constraint, test: (V, T) -> Boolean): ConstraintHelper
}

/** Builder step that can manipulate the constraint. */
interface ConstraintHelper {
    /** Changes the message key of the constraint. */
    infix fun messageKey(key: String)
}

@DslMarker
annotation class Marker

@Marker
@Suppress("TooManyFunctions")
class ValidationBuilder<T, V> : ConstraintBuilder<V>, ExtConstraintBuilder<T, V> {

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

    override fun validate(constraint: Constraint, test: (V) -> Boolean): ConstraintHelper {
        return validate(constraint) { v, _ -> test(v) }
    }

    override fun validate(constraint: Constraint, test: (V, T) -> Boolean): ConstraintHelper {
        val validator = ConstraintValidator(constraint, test)
        validators += validator
        return ConstraintHelperImpl(validator, this)
    }

    internal fun addValidator(validator: PropertyValidator<V, T>) {
        validators += validator
    }

    internal fun updateValidator(old: PropertyValidator<V, T>, new: PropertyValidator<V, T>) {
        val pos = validators.lastIndexOf(old)
        check(pos >= 0)
        validators[pos] = new
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

private class ConstraintHelperImpl<T, V>(
    val validator: ConstraintValidator<V, T>,
    val builder: ValidationBuilder<T, V>
) : ConstraintHelper {

    override infix fun messageKey(key: String) {
        builder.updateValidator(validator, validator.updateMessageKey(key))
    }
}
