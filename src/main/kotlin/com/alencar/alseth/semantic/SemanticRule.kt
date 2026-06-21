package com.alencar.alseth.semantic

/**
 * A meaning recovered from a compact symbol.
 *
 * The compact byte does not store this whole payload. It only addresses it
 * through the rules shared by a ContextualSemanticSchema.
 */
data class SemanticMeaning(
    val key: String,
    val summary: String,
    val expansion: List<String> = emptyList(),
    val confidence: Double = 1.0
)

/**
 * Describes when a state bit means something.
 *
 * This is the core idea: the same state bit can point to different meanings
 * depending on the active dimensional bits and on the surrounding state bits.
 */
data class SemanticRule(
    val targetStateBit: Int,
    val expectedTargetValue: Boolean,
    val requiredDimensionMask: Int = 0,
    val blockedDimensionMask: Int = 0,
    val requiredStateMask: Int = 0,
    val blockedStateMask: Int = 0,
    val meaning: SemanticMeaning
) {
    init {
        require(targetStateBit in 0..3) { "targetStateBit must be between 0 and 3." }
        requireNibble(requiredDimensionMask, "requiredDimensionMask")
        requireNibble(blockedDimensionMask, "blockedDimensionMask")
        requireNibble(requiredStateMask, "requiredStateMask")
        requireNibble(blockedStateMask, "blockedStateMask")
        require((requiredDimensionMask and blockedDimensionMask) == 0) {
            "A dimension bit cannot be both required and blocked."
        }
        require((requiredStateMask and blockedStateMask) == 0) {
            "A state bit cannot be both required and blocked."
        }
    }

    fun matches(symbol: EightBitSemanticSymbol): Boolean {
        val targetMatches = symbol.hasStateBit(targetStateBit) == expectedTargetValue
        val dimensionMatches = containsAll(symbol.dimensionNibble, requiredDimensionMask) &&
            containsNone(symbol.dimensionNibble, blockedDimensionMask)
        val stateMatches = containsAll(symbol.stateNibble, requiredStateMask) &&
            containsNone(symbol.stateNibble, blockedStateMask)

        return targetMatches && dimensionMatches && stateMatches
    }

    private fun containsAll(value: Int, mask: Int): Boolean = (value and mask) == mask

    private fun containsNone(value: Int, mask: Int): Boolean = (value and mask) == 0

    private fun requireNibble(value: Int, name: String) {
        require(value in 0..0x0F) { "$name must be between 0 and 15." }
    }
}
