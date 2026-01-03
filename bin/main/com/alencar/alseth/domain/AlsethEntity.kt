package com.alencar.alseth.domain

import com.alencar.alseth.core.BitmaskEngine
import com.alencar.alseth.config.DimensionSchema

/**
 * Entidade Alseth (Refatorada)
 * * Agora capaz de interagir com Schemas dinâmicos para debug,
 * mas mantendo a performance crua nas operações de verificação.
 */
data class AlsethEntity(
    val id: String,
    val alias: String,
    private var _quantumState: Int = 0
) {
    
    val state: Int
        get() = _quantumState

    // --- OPERAÇÕES CORE (Alta Performance) ---

    fun grant(dimension: Int, atom: Int) {
        _quantumState = BitmaskEngine.merge(_quantumState, dimension, atom)
    }

    fun revokeDimension(dimension: Int) {
        _quantumState = BitmaskEngine.strip(_quantumState, dimension)
    }

    fun can(dimension: Int, atom: Int): Boolean {
        return BitmaskEngine.collapse(_quantumState, dimension, atom)
    }

    // --- FERRAMENTAS DE DESENVOLVIMENTO (DX) ---

    /**
     * Debug Poderoso: Recebe o Schema do cliente e "traduz" o estado atual.
     * Útil para logs e auditoria.
     */
    fun debugState(schema: DimensionSchema): String {
        return buildString {
            append("=== Alseth Entity Inspector ===\n")
            append("User: $alias ($id)\n")
            append("Raw State: $_quantumState\n")
            
            // Decodifica quais dimensões estão ativas
            val activeContexts = schema.getContexts().filter { (_, mask) -> 
                (mask != 0) && (_quantumState and mask) != 0 
            }
            
            if (activeContexts.isEmpty()) {
                append("Active Dimensions: [NONE or GLOBAL ONLY]\n")
            } else {
                append("Active Dimensions:\n")
                activeContexts.forEach { (name, mask) ->
                    append("  - $name (Mask: $mask)\n")
                    // Tenta adivinhar átomos ativos nesta dimensão (heurística simples)
                    val activeAtoms = schema.getAtoms().filter { (_, atomVal) ->
                        (_quantumState and atomVal) != 0
                    }
                    if (activeAtoms.isNotEmpty()) {
                         append("    -> Atoms: ${activeAtoms.keys.joinToString(", ")}\n")
                    }
                }
            }
            append("===============================")
        }
    }
}