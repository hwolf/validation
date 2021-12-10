package hwolf.validation

import kotlin.reflect.full.declaredMemberProperties

/**
 * A validation constraint.
 */
interface Constraint {

    /**
     * Specifies the message key.
     */
    val messageKey: String get() = "${this::class.qualifiedName}.message"

    /**
     * Specifies the parameters to replace in the message.
     */
    val parameters: Map<String, Any?>
        get() = this::class.declaredMemberProperties
            .filter { Constraint::class.declaredMemberProperties.none { p -> p.name == it.name } }
            .associate { it.name to it.call(this) }
}

object Required : Constraint

/**
 * Default implementation of [Constraint]
 */
data class DefaultConstraint(
    override val messageKey: String,
    override val parameters: Map<String, Any?>
) : Constraint
