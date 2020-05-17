package org.inframincer.realtimetube.source

import java.lang.IllegalArgumentException
import java.util.Locale


class Search {

    enum class SearchProvider {
        NAVER,
        YAHOO,
        GOOGLE,
    }

    companion object {

        fun getAvailableLocales() = listOf(Locale.KOREA, Locale.JAPAN, Locale.US)

        fun getSearchProvider(targetLocale: Locale): SearchProvider {
            return when (targetLocale) {
                Locale.KOREA -> SearchProvider.NAVER
                Locale.JAPAN -> SearchProvider.YAHOO
                Locale.US -> SearchProvider.GOOGLE
                else -> throw IllegalArgumentException("unsupported locale")
            }
        }
    }
}
