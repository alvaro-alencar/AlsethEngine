package com.alencar.alseth.semantic

/**
 * Represents the first experimental Alseth semantic-compression unit.
 *
 * The byte is intentionally split in two nibbles:
 *
 * - low nibble  (bits 0..3): analyzable state bits;
 * - high nibble (bits 4..7): contextual dimension bits.
 *
 * This models the insight that a small symbol does not contain a whole idea.
 * It points to a meaning that can only be expanded through a shared contextual schema.
 */
data class EightBitSemanticSymbol private constructor(
    val raw: Int
) {
    init {
        require(raw in 0..0xFF) { "EightBitSemanticSymbol raw value must be between 0 and 255." }
    }

    val stateNibble: Int
        get() = raw and STATE_MASK

    val dimensionNibble: Int
        get() = (raw and DIMENSION_MASK) ushr 4

    fun hasStateBit(index: Int): Boolean {
        validateNibbleIndex(index)
        return (stateNibble and (1 shl index)) != 0
    }

    fun hasDimensionBit(index: Int): Boolean {
        validateNibbleIndex(index)
        return (dimensionNibble and (1 shl index)) != 0
    }

    fun toBinaryString(): String = raw.toString(2).padStart(8, '0')

    companion object {
        private const val STATE_MASK = 0x0F
        private const val DIMENSION_MASK = 0xF0

        fun fromRaw(raw: Int): EightBitSemanticSymbol = EightBitSemanticSymbol(raw)

        fun fromNibbles(stateNibble: Int, dimensionNibble: Int): EightBitSemanticSymbol {
            require(stateNibble in 0..0x0F) { "stateNibble must be between 0 and 15." }
            require(dimensionNibble in 0..0x0F) { "dimensionNibble must be between 0 and 15." }
            return EightBitSemanticSymbol((dimensionNibble shl 4) or stateNibble)
        }

        private fun validateNibbleIndex(index: Int) {
            require(index in 0..3) { "Bit index must be between 0 and 3." }
        }
    }
}
