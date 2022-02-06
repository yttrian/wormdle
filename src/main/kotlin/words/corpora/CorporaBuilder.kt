package org.yttr.lordle.words.corpora

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpStatement
import java.io.File
import java.util.zip.ZipFile

object CorporaBuilder {
    private val client = HttpClient()

    private val corpora = listOf(
        Corpus(
            "words",
            "https://raw.githubusercontent.com/nltk/nltk_data/gh-pages/packages/corpora/words.zip",
            "words/en"
        )
    )

    /**
     * Download and extract words from corpora
     */
    suspend fun downloadCorporaAndExtract() = corpora.flatMap { corpus ->
        val httpStatement = client.get<HttpStatement>(corpus.url)
        val responseBody = httpStatement.receive<ByteArray>()
        val file = File.createTempFile(corpus.name, null)
        file.writeBytes(responseBody)
        ZipFile(file).use {
            val entry = it.getEntry(corpus.innerFile)
            String(it.getInputStream(entry).readAllBytes()).lines()
        }
    }

    suspend fun buildWordSet(length: Int) = downloadCorporaAndExtract().filter { it.length == length }.toSet()
}
