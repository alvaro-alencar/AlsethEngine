package com.alencar.alseth.config

/**
 * Alseth Dimensions
 * Define os contextos de realidade onde os bits operam.
 *
 * Estrutura de 32 bits:
 * [31..28] -> Reservado para Identificador de Contexto (4 bits = 16 dimensões possíveis)
 * [27..00] -> Payload de dados (Flags e Permissões)
 */
object Dimensions {
    // --- MÁSCARAS DE CONTEXTO (High-Order Bits) ---
    // Usamos Hexadecimal para clareza visual dos bits superiores
    
    const val GLOBAL   = 0x00000000 // Dimensão Base (Sempre verdade)
    const val AUTH     = 0x10000000 // Dimensão de Autenticação/Login
    const val MEDIA    = 0x20000000 // Dimensão de Mídia (Video/Audio/PDF)
    const val SOCIAL   = 0x40000000 // Dimensão Social (Ranking/Badges)
    const val TEMP     = 0x80000000.toInt() // Dimensão Temporal (Acessos provisórios)

    // --- ÁTOMOS (Low-Order Bits) ---
    // Estes bits mudam de significado dependendo da Dimensão acima.
    
    // Átomos Genéricos (Significam coisas diferentes em cada lugar)
    const val ATOM_ALPHA = 0x00000001 // 1
    const val ATOM_BETA  = 0x00000002 // 2
    const val ATOM_GAMMA = 0x00000004 // 4
    const val ATOM_DELTA = 0x00000008 // 8
    
    // Atalhos Semânticos (Opcional, para facilitar leitura no código final)
    // No contexto AUTH:
    const val PERM_READ  = ATOM_ALPHA
    const val PERM_WRITE = ATOM_BETA
    
    // No contexto MEDIA:
    const val HAS_VIDEO  = ATOM_ALPHA
    const val HAS_PDF    = ATOM_BETA
}