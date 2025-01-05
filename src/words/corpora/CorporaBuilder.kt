package org.yttr.lordle.words.corpora

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.slf4j.LoggerFactory
import java.io.File
import java.util.zip.ZipFile

object CorporaBuilder {
    private val logger = LoggerFactory.getLogger(javaClass.simpleName)
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
        runCatching {
            val httpStatement = client.get<HttpStatement>(corpus.url)
            val responseBody = httpStatement.receive<ByteArray>()
            val file = File.createTempFile(corpus.name, null)
            file.writeBytes(responseBody)
            ZipFile(file).use { zipFile ->
                corpus.innerFiles.flatMap { innerFile ->
                    zipFile.getEntry(innerFile)?.let { zipEntry ->
                        String(zipFile.getInputStream(zipEntry).readAllBytes()).lines()
                    } ?: emptyList()
                }
            }
        }.onFailure {
            logger.warn("Failed to process \"${corpus.name}\" corpus!", it)
        }.getOrElse {
            emptyList()
        }
    }

    suspend fun buildWordSet(length: Int) = downloadCorporaAndExtract().filter { it.length == length }.toSet()
}
