package com.alencar.alseth.semantic

/**
 * Small facade for creating and expanding contextual symbols.
 *
 * This class intentionally does not pretend to compress arbitrary human meaning.
 * It compresses meaning only when a schema already exists as the shared
 * decompression key.
 */
class SemanticCompressor(
    private val schema: ContextualSemanticSchema
) {
    fun symbol(stateNibble: Int, dimensionNibble: Int): EightBitSemanticSymbol {
        return EightBitSemanticSymbol.fromNibbles(stateNibble, dimensionNibble)
    }

    fun expand(raw: Int): SemanticExpansion {
        return schema.expand(EightBitSemanticSymbol.fromRaw(raw))
    }

    fun expand(stateNibble: Int, dimensionNibble: Int): SemanticExpansion {
        return schema.expand(symbol(stateNibble, dimensionNibble))
    }
}
