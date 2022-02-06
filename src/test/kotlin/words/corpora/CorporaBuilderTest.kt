package org.yttr.lordle.words.corpora

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertContains

internal class CorporaBuilderTest {
    @Test
    fun `downloads and extracts words`() = runBlocking {
        val words = CorporaBuilder.downloadCorporaAndExtract()
        assertContains(words, "vexed")
    }
}
