package hwolf.kvalidation.icu

import com.ibm.icu.util.UResourceBundle
import hwolf.kvalidation.MessageInterpolator
import hwolf.kvalidation.MessageSource
import org.tinylog.kotlin.Logger
import java.util.*
import java.util.concurrent.ConcurrentHashMap

fun messageInterpolator(vararg baseNames: String) =
    MessageInterpolator(
        messageSource = ResourceBundleMessageSource(baseNames.toList()),
        messageFormatter = DefaultMessageFormatter)

private typealias ResourceBundleMap = MutableMap<Locale, UResourceBundle?>

private val cachedResourceBundles = ConcurrentHashMap<String, ResourceBundleMap>()

class ResourceBundleMessageSource(
    private val baseNames: List<String>
) : MessageSource {

    constructor(baseName: String) : this(listOf(baseName))

    override fun invoke(code: String, locale: Locale): String? =
        baseNames.firstNotNullOfOrNull { basename ->
            getResourceBundle(basename, locale)?.let {
                getStringOrNull(it, code)
            }
        }

    private fun getResourceBundle(baseName: String, locale: Locale) =
        getResourceBundle(getLocaleMap(baseName), baseName, locale)

    private fun getResourceBundle(localeMap: ResourceBundleMap, baseName: String, locale: Locale) =
        localeMap.computeIfAbsent(locale) {
            loadResourceBundle(baseName, it)
        }

    private fun getLocaleMap(baseName: String) =
        cachedResourceBundles.computeIfAbsent(baseName) {
            ConcurrentHashMap()
        }

    private fun loadResourceBundle(baseName: String, locale: Locale) =
        try {
            UResourceBundle.getBundleInstance(baseName, locale)
        } catch (ex: MissingResourceException) {
            // Assume bundle not found
            Logger.warn { "ResourceBundle $baseName not found for MessageSource: ${ex.message}" }
            null
        }

    private fun getStringOrNull(bundle: UResourceBundle, key: String): String? =
        try {
            bundle.getString(key)
        } catch (ex: MissingResourceException) {
            // Assume key not found for some other reason
            null
        }
}
