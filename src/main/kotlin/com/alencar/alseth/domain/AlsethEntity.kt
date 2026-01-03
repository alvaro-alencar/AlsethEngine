package com.alencar.alseth.domain

import com.alencar.alseth.core.BitmaskEngine

/**
 * Representa qualquer entidade que possua um Estado Alseth.
 * Pode ser um User, um Produto, uma Aula ou um Dispositivo IoT.
 */
data class AlsethEntity(
    val id: String,
    val alias: String, // Nome legível (ex: "Álvaro")
    private var _quantumState: Int = 0 // Estado protegido
) {
    
    // Acesso público somente leitura ao estado (para persistência em DB)
    val state: Int
        get() = _quantumState

    /**
     * Adiciona permissões/características em uma dimensão específica.
     */
    fun grant(dimension: Int, atom: Int) {
        _quantumState = BitmaskEngine.merge(_quantumState, dimension, atom)
    }

    /**
     * Remove uma dimensão inteira (Revoga poderes).
     */
    fun revokeDimension(dimension: Int) {
        _quantumState = BitmaskEngine.strip(_quantumState, dimension)
    }

    /**
     * A pergunta mágica: "Neste contexto, eu posso fazer isso?"
     */
    fun can(dimension: Int, atom: Int): Boolean {
        return BitmaskEngine.collapse(_quantumState, dimension, atom)
    }

    /**
     * Retorna uma representação binária visual para debug.
     */
    fun dumpMatrix(): String {
        return buildString {
            append("Entity: $alias [ID: $id]\n")
            append("State (INT): $_quantumState\n")
            append("Binary: ${Integer.toBinaryString(_quantumState).padStart(32, '0')}\n")
        }
    }
    /**
     * Projeta o estado para um cliente específico, removendo dimensões secretas.
     * Ex: O Frontend não precisa saber sobre flags internas de servidor.
     */
    fun exportState(forbiddenDimensions: Int = 0): Int {
        // Retorna o estado limpo das dimensões proibidas
        return _quantumState and forbiddenDimensions.inv()
    }
}