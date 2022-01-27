package hwolf.kvalidation.icu

import com.ibm.icu.text.MessageFormat
import hwolf.kvalidation.Constraint
import hwolf.kvalidation.ConstraintViolation
import hwolf.kvalidation.Locale
import hwolf.kvalidation.MessageFormatter
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

object DefaultMessageFormatter : MessageFormatter {

    override fun invoke(template: String, violation: ConstraintViolation, locale: Locale): String =
        MessageFormat(template, locale).format(buildArguments(violation))

    private fun buildArguments(violation: ConstraintViolation): Map<String, Any?> =
        getArguments(violation.constraint, Constraint::class) +
                getArguments(violation, Any::class)

    private fun getArguments(bean: Any, base: KClass<*>): Map<String, Any?> =
        bean::class.memberProperties
            .filter { property ->
                base.declaredMemberProperties.none { it.name == property.name }
            }
            .associate { property ->
                property.name to property.call(bean)
            }
}
