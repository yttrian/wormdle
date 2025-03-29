package org.yttr.wormdle.words

import java.util.zip.ZipInputStream

class Corpus(private val zip: String = "/corpus.zip", private val file: String = "words/en") {
    /**
     * Get all the words from the provided corpus
     */
    fun getWords(): Set<String> {
        val resource = requireNotNull(this.javaClass.getResourceAsStream(zip)) {
            "Resource $zip not found!"
        }

        val words = buildSet {
            // Open the zip
            resource.use { stream ->
                ZipInputStream(stream).use { zip ->
                    var entry = zip.nextEntry

                    while (entry != null) {
                        // Find the file
                        if (entry.name == file) {
                            zip.bufferedReader().use { reader ->
                                // Extract all the lines
                                addAll(reader.lineSequence())
                            }
                            break
                        }

                        zip.closeEntry()
                        entry = zip.nextEntry
                    }
                }
            }
        }

        check(words.isNotEmpty()) { "No words found in $file in $zip!" }

        return words
    }
}
