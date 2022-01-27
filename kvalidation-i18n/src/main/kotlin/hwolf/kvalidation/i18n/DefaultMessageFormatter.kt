package hwolf.kvalidation.i18n

import com.mitchellbosecke.pebble.PebbleEngine
import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.Locale
import hwolf.kvalidation.MessageFormatter
import java.io.StringWriter

object DefaultMessageFormatter : MessageFormatter {

    private val engine = PebbleEngine.Builder().build()

    override fun invoke(template: String, violation: ConstraintViolation, locale: Locale) =
        StringWriter().apply {
            engine.getLiteralTemplate(template).evaluate(
                this,
                mapOf("violation" to violation, "constraint" to violation.constraint),
                locale)
        }.toString()
}
