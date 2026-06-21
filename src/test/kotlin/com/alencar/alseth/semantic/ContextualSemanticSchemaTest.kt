package com.alencar.alseth.semantic

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ContextualSemanticSchemaTest {

    @Test
    fun `same state bit expands to different meanings by dimension`() {
        val schema = ContextualSemanticSchema.of(
            SemanticRule(
                targetStateBit = 0,
                expectedTargetValue = true,
                requiredDimensionMask = bit(0),
                meaning = SemanticMeaning(
                    key = "cross-as-christian-symbol",
                    summary = "A compact sign points to a Christian interpretive universe.",
                    expansion = listOf("Bible", "Christ", "sacrifice", "resurrection", "community", "moral horizon")
                )
            ),
            SemanticRule(
                targetStateBit = 0,
                expectedTargetValue = true,
                requiredDimensionMask = bit(1),
                meaning = SemanticMeaning(
                    key = "cross-as-geometric-form",
                    summary = "The same visible mark is interpreted as a geometric shape.",
                    expansion = listOf("vertical line", "horizontal line", "intersection")
                )
            )
        )

        val compressor = SemanticCompressor(schema)

        val religious = compressor.expand(stateNibble = bit(0), dimensionNibble = bit(0))
        val geometric = compressor.expand(stateNibble = bit(0), dimensionNibble = bit(1))

        assertEquals(listOf("cross-as-christian-symbol"), religious.meanings.map { it.key })
        assertEquals(listOf("cross-as-geometric-form"), geometric.meanings.map { it.key })
    }

    @Test
    fun `state bit can mean something when another state bit changes`() {
        val schema = ContextualSemanticSchema.of(
            SemanticRule(
                targetStateBit = 0,
                expectedTargetValue = false,
                requiredStateMask = bit(1),
                meaning = SemanticMeaning(
                    key = "absence-modified-by-neighbor",
                    summary = "Bit zero being off becomes meaningful because bit one is on."
                )
            ),
            SemanticRule(
                targetStateBit = 0,
                expectedTargetValue = false,
                blockedStateMask = bit(1),
                meaning = SemanticMeaning(
                    key = "pure-absence",
                    summary = "Bit zero being off means a different thing when bit one is also off."
                )
            )
        )

        val compressor = SemanticCompressor(schema)

        val withSecondBit = compressor.expand(stateNibble = bit(1), dimensionNibble = 0)
        val withoutSecondBit = compressor.expand(stateNibble = 0, dimensionNibble = 0)

        assertEquals("absence-modified-by-neighbor", withSecondBit.meanings.single().key)
        assertEquals("pure-absence", withoutSecondBit.meanings.single().key)
    }

    @Test
    fun `symbol keeps state and dimension nibbles separated`() {
        val symbol = EightBitSemanticSymbol.fromNibbles(
            stateNibble = 0b0001,
            dimensionNibble = 0b1000
        )

        assertEquals("10000001", symbol.toBinaryString())
        assertTrue(symbol.hasStateBit(0))
        assertFalse(symbol.hasStateBit(1))
        assertTrue(symbol.hasDimensionBit(3))
        assertFalse(symbol.hasDimensionBit(0))
    }

    private fun bit(index: Int): Int = 1 shl index
}
