package com.alencar.alseth.config

/**
 * Interface que define o contrato para qualquer universo de regras.
 * Isso permite que o cliente crie suas próprias Dimensões (IoT, Financeiro, Saúde)
 * sem alterar o código fonte do Alseth.
 */
interface DimensionSchema {
    // Retorna o mapa de "Nome Legível" -> "Valor Inteiro da Dimensão"
    fun getContexts(): Map<String, Int>
    
    // Retorna o mapa de "Nome do Átomo" -> "Valor do Bit"
    fun getAtoms(): Map<String, Int>

    // Valida se não há colisão de bits (Segurança do Produto)
    fun validate() {
        // Implementaremos a lógica para garantir que o cliente 
        // não usou o mesmo bit para duas coisas diferentes.
    }
}

/**
 * Implementação Padrão (Default)
 * Serve como exemplo ou fallback para quem não configurar nada.
 * (Antigo Dimensions.kt transformado em classe instanciável)
 */
class DefaultSchema : DimensionSchema {
    
    companion object {
        // Mantemos as constantes para uso interno se necessário
        const val AUTH_DIMENSION = 0x10000000
        const val MEDIA_DIMENSION = 0x20000000
    }

    override fun getContexts(): Map<String, Int> {
        return mapOf(
            "GLOBAL" to 0x00000000,
            "AUTH"   to AUTH_DIMENSION,
            "MEDIA"  to MEDIA_DIMENSION,
            "SOCIAL" to 0x40000000,
            "TEMP"   to -0x80000000 // 0x80000000.toInt()
        )
    }

    override fun getAtoms(): Map<String, Int> {
        return mapOf(
            "ATOM_ALPHA" to 0x00000001,
            "ATOM_BETA"  to 0x00000002,
            "ATOM_GAMMA" to 0x00000004,
            "ATOM_DELTA" to 0x00000008
        )
    }
}