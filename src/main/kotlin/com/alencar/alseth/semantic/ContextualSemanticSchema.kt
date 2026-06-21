package com.alencar.alseth.semantic

/**
 * Shared key that expands compact symbols into meanings.
 *
 * Analogy: a cross on a wall is visually tiny, but a community that owns the
 * interpretive key can expand it into a whole network of doctrine, memory,
 * values and expectations.
 */
class ContextualSemanticSchema(
    private val rules: List<SemanticRule>
) {
    init {
        require(rules.isNotEmpty()) { "At least one semantic rule is required." }
    }

    fun expand(symbol: EightBitSemanticSymbol): SemanticExpansion {
        val meanings = rules
            .filter { it.matches(symbol) }
            .map { it.meaning }
            .distinctBy { it.key }

        return SemanticExpansion(
            symbol = symbol,
            meanings = meanings
        )
    }

    companion object {
        fun of(vararg rules: SemanticRule): ContextualSemanticSchema {
            return ContextualSemanticSchema(rules.toList())
        }
    }
}

/**
 * Result of decompressing a compact symbol through a contextual schema.
 */
data class SemanticExpansion(
    val symbol: EightBitSemanticSymbol,
    val meanings: List<SemanticMeaning>
) {
    val isEmpty: Boolean
        get() = meanings.isEmpty()

    fun summaries(): List<String> = meanings.map { it.summary }

    fun explain(): String = buildString {
        appendLine("Symbol: ${symbol.toBinaryString()}")
        appendLine("State nibble: ${symbol.stateNibble.toString(2).padStart(4, '0')}")
        appendLine("Dimension nibble: ${symbol.dimensionNibble.toString(2).padStart(4, '0')}")
        if (meanings.isEmpty()) {
            appendLine("Meanings: [none]")
        } else {
            appendLine("Meanings:")
            meanings.forEach { meaning ->
                appendLine("- ${meaning.key}: ${meaning.summary}")
                meaning.expansion.forEach { item -> appendLine("  - $item") }
            }
        }
    }.trimEnd()
}
