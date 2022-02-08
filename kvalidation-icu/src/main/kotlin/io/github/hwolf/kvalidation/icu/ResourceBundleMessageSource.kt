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
package io.github.hwolf.kvalidation.icu

import com.ibm.icu.util.UResourceBundle
import io.github.hwolf.kvalidation.MessageInterpolator
import io.github.hwolf.kvalidation.MessageSource
import org.tinylog.kotlin.Logger
import java.util.*
import java.util.concurrent.ConcurrentHashMap

fun messageInterpolator(vararg baseNames: String) =
    MessageInterpolator(
        messageSource = ResourceBundleMessageSource(baseNames.toList()),
        messageFormatter = DefaultMessageFormatter)

private typealias ResourceBundleMap = MutableMap<Locale, UResourceBundle?>

private val cachedBundles = ConcurrentHashMap<String, ResourceBundleMap>()

class ResourceBundleMessageSource(
    private val baseNames: List<String>
) : MessageSource {

    constructor(baseName: String) : this(listOf(baseName))

    override fun invoke(code: String, locale: Locale): String? =
        baseNames.firstNotNullOfOrNull { basename ->
            getBundle(basename, locale)?.let {
                getStringOrNull(it, code)
            }
        }

    private fun getBundle(baseName: String, locale: Locale) =
        getBundle(getLocaleMap(baseName), baseName, locale)

    private fun getBundle(localeMap: ResourceBundleMap, baseName: String, locale: Locale) =
        localeMap.computeIfAbsent(locale) {
            loadResourceBundle(baseName, it)
        }

    private fun getLocaleMap(baseName: String) =
        cachedBundles.computeIfAbsent(baseName) {
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
