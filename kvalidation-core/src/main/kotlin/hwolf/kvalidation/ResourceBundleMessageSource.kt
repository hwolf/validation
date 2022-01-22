package hwolf.kvalidation

import org.tinylog.kotlin.Logger
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap

private typealias ResourceBundleMap = MutableMap<Locale, ResourceBundle?>

private val cachedResourceBundles = ConcurrentHashMap<String, ResourceBundleMap>()

class ResourceBundleMessageSource(
    private val baseNames: List<String>,
    private val encoding: Charset = StandardCharsets.ISO_8859_1
) : MessageSource {

    constructor(baseName: String, encoding: Charset = StandardCharsets.ISO_8859_1) : this(listOf(baseName), encoding)

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
            ResourceBundle.getBundle(baseName, locale, MessageSourceControl(encoding))
        } catch (ex: MissingResourceException) {
            // Assume bundle not found
            Logger.warn { "ResourceBundle $baseName not found for MessageSource: ${ex.message}" }
            null
        }

    private fun getStringOrNull(bundle: ResourceBundle, key: String): String? =
        try {
            bundle.getString(key)
        } catch (ex: MissingResourceException) {
            // Assume key not found for some other reason
            null
        }
}

private class MessageSourceControl(
    val encoding: Charset
) : ResourceBundle.Control() {

    @Throws(IllegalAccessException::class, InstantiationException::class, IOException::class)
    override fun newBundle(baseName: String, locale: Locale, format: String, loader: ClassLoader, reload: Boolean) =
        when (format != "java.properties") {
            true -> super.newBundle(baseName, locale, format, loader, reload)
            else -> getStream(loader,
                toResourceName(toBundleName(baseName, locale), "properties"),
                reload)?.let(::loadStream)
        }

    private fun getStream(loader: ClassLoader, resourceName: String, reload: Boolean) =
        loader.getResource(resourceName)?.let { url ->
            url.openConnection().let { conn ->
                if (reload) {
                    conn.useCaches = false
                }
                conn.getInputStream()
            }
        }

    private fun loadStream(stream: InputStream) = InputStreamReader(stream, encoding).use(::PropertyResourceBundle)

    override fun getFallbackLocale(baseName: String, locale: Locale) = null

    override fun getTimeToLive(baseName: String, locale: Locale) = TTL_DONT_CACHE
}
