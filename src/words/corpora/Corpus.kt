package org.yttr.lordle.words.corpora

data class Corpus(val name: String, val url: String, val innerFiles: List<String>) {
    constructor(name: String, url: String, vararg innerFiles: String) : this(name, url, innerFiles.toList())
}
