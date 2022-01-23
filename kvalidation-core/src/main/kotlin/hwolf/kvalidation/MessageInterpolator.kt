package hwolf.kvalidation

typealias Locale = java.util.Locale
typealias MessageSource = (code: String, locale: Locale) -> String?
typealias MessageFormatter = (template: String, violation: ConstraintViolation, locale: Locale) -> String
typealias MessageCodeResolver = (violation: ConstraintViolation) -> List<String>

fun messageSourceMap(vararg messages: Pair<String, String>) = messageSourceMap(mapOf(*messages))
fun messageSourceMap(messages: Map<String, String>): MessageSource = { code, _ -> messages[code] }

class MessageInterpolator(
    private val messageSource: MessageSource,
    private val messageFormatter: MessageFormatter,
    private val messageCodeResolver: MessageCodeResolver = DefaultMessageCodeResolver
) {
    fun interpolate(violation: ConstraintViolation, locale: Locale): String =
        messageFormatter(
            findMessageTemplate(violation.constraint.messageKey, messageCodeResolver(violation), locale),
            violation,
            locale)

    private fun findMessageTemplate(defaultMessage: String, codes: Iterable<String>, locale: Locale) =
        codes.firstNotNullOfOrNull { code ->
            messageSource(code, locale)
        } ?: defaultMessage
}
