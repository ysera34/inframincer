package org.inframincer.realtimetube.source.parser

import org.inframincer.realtimetube.source.Search.SearchProvider

abstract class Parser {

    companion object {

        fun getParser(searchProvider: SearchProvider): Parser {
            return when (searchProvider) {
                SearchProvider.NAVER -> NParser()
                SearchProvider.YAHOO -> YParser()
                SearchProvider.GOOGLE -> GParser()
            }
        }
    }

    abstract fun parse(arg: String): List<String>
}
