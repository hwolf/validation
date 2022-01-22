package hwolf.kvalidation

/** A validation constraint. */
interface Constraint {

    /** Specifies the message key. */
    val messageKey: String get() = this::class.qualifiedName ?: "<unknown>"
}

/** A constraint that validates a value is required (not null). */
object Required : Constraint
