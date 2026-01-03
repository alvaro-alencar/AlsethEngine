package com.alencar.alseth.core

/**
 * BitmaskEngine (Refatorado)
 * * Agora completamente desacoplado de regras de negócio.
 * Ele opera puramente na lógica booleana e aritmética.
 * * Performance: Mantém O(1).
 */
object BitmaskEngine {

    // Constante local para definir o conceito de Global (Zero)
    private const val DIMENSION_GLOBAL = 0

    /**
     * O Colapso da Função de Onda.
     */
    fun collapse(currentState: Int, targetDimension: Int, targetAtom: Int): Boolean {
        // 1. Isolamento Dimensional:
        // Verifica se a dimensão está ativa no estado OU se é a dimensão Global (0).
        // Agora não dependemos mais de "Dimensions.kt".
        val hasDimension = (currentState and targetDimension) != 0 || targetDimension == DIMENSION_GLOBAL

        // 2. Verificação Atômica:
        return if (hasDimension) {
            (currentState and targetAtom) != 0
        } else {
            false
        }
    }

    /**
     * Fusão de Realidade.
     */
    fun merge(currentState: Int, dimension: Int, atoms: Int): Int {
        return currentState or dimension or atoms
    }

    /**
     * Extração de Realidade (Sanitização).
     */
    fun strip(currentState: Int, dimension: Int): Int {
        return currentState and dimension.inv()
    }
}