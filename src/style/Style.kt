package org.yttr.lordle.style

import kotlinx.css.CssBuilder
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

abstract class Style {
    abstract fun CssBuilder.apply()

    fun CssBuilder.append(style: Style) {
        with(style) { apply() }
    }

    val content by lazy {
        CssBuilder().apply { apply() }.toString()
    }

    val contentHash by lazy {
        val digest = MessageDigest.getInstance("MD5")
        val base64 = Base64.getEncoder()
        val input = base64.encode(content.toByteArray())
        val digestInteger = BigInteger(1, digest.digest(input))
        val hash = String.format(Locale.ROOT, "%08x", digestInteger).substring(0, CONTENT_HASH_LENGTH)
        hash
    }

    companion object {
        private const val CONTENT_HASH_LENGTH = 8
    }
}
