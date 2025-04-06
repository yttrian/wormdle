package org.yttr.wormdle.game

import kotlin.test.Test
import kotlin.test.assertContentEquals

internal class MarkerTest {
    @Test
    fun `all when correct`() {
        val expected = listOf(Marker.Here, Marker.Here, Marker.Here)
        assertContentEquals(expected, Marker.generate("Xûr", "Xûr"))
    }

    @Test
    fun `none when all wrong`() {
        val expected = listOf(Marker.Nowhere, Marker.Nowhere, Marker.Nowhere)
        assertContentEquals(expected, Marker.generate("abc", "xyz"))
    }

    @Test
    fun `marks out of order letters`() {
        val expected = listOf(Marker.Somewhere, Marker.Nowhere, Marker.Nowhere, Marker.Nowhere, Marker.Nowhere)
        assertContentEquals(expected, Marker.generate("ghost", "titan"))
    }

    @Test
    fun `marks first valid repeats`() {
        val expected =
            listOf(Marker.Somewhere, Marker.Here, Marker.Somewhere, Marker.Nowhere, Marker.Nowhere, Marker.Here)
        assertContentEquals(expected, Marker.generate("dabble", "baddie"))
    }

    @Test
    fun `marks tax for cat`() {
        val expected = listOf(Marker.Somewhere, Marker.Here, Marker.Nowhere)
        assertContentEquals(expected, Marker.generate("cat", "tax"))
    }
}
