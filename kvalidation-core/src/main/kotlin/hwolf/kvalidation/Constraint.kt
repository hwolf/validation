package hwolf.kvalidation

import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

/**
 * A validation constraint.
 */
interface Constraint {

    /**
     * Specifies the parameters to replace in the message.
     */
    val parameters: Map<String, Any?>
        get() = this::class.memberProperties
            .filter { Constraint::class.declaredMemberProperties.none { p -> p.name == it.name } }
            .associate { it.name to it.call(this) }
}

/**
 * A constraint that validates a value is required (not null).
 */
object Required : Constraint
