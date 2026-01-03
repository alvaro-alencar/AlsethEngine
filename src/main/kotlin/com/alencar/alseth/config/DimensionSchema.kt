package com.alencar.alseth.config

/**
 * DimensionSchema: O Contrato do Universo Alseth.
 *
 * Para transformar o Alseth em um produto SaaS/Lib, removemos as constantes "chumbadas"
 * e criamos esta interface. O cliente implementa isso para definir suas próprias regras
 * de negócio (Dimensões e Átomos) sem tocar no código fonte do motor.
 */
interface DimensionSchema {
    /**
     * Retorna um mapa contendo o nome legível do contexto e seu valor binário (máscara).
     * Ex: "AUTH" -> 0x10000000
     */
    fun getContexts(): Map<String, Int>

    /**
     * Retorna um mapa contendo o nome legível do átomo (permissão/dado) e seu valor.
     * Ex: "READ_PERMISSION" -> 0x00000001
     */
    fun getAtoms(): Map<String, Int>
}

/**
 * DefaultSchema: Implementação de Referência.
 *
 * Reproduz a configuração original do projeto para garantir que o sistema
 * funcione "out of the box" para novos usuários ou testes.
 */
class DefaultSchema : DimensionSchema {

    companion object {
        // Constantes mantidas para uso interno seguro se necessário,
        // mas o sistema deve preferir usar os métodos da interface.
        const val DIMENSION_AUTH     = 0x10000000
        const val DIMENSION_MEDIA    = 0x20000000
        const val DIMENSION_SOCIAL   = 0x40000000
        const val DIMENSION_TEMP     = -0x80000000 // 0x80000000.toInt()
        
        // Exemplo de atoms comuns
        const val ATOM_ALPHA = 0x00000001
        const val ATOM_BETA  = 0x00000002
    }

    override fun getContexts(): Map<String, Int> {
        return mapOf(
            "GLOBAL" to 0x00000000,
            "AUTH"   to DIMENSION_AUTH,
            "MEDIA"  to DIMENSION_MEDIA,
            "SOCIAL" to DIMENSION_SOCIAL,
            "TEMP"   to DIMENSION_TEMP
        )
    }

    override fun getAtoms(): Map<String, Int> {
        return mapOf(
            "ATOM_ALPHA" to ATOM_ALPHA,
            "ATOM_BETA"  to ATOM_BETA,
            "ATOM_GAMMA" to 0x00000004,
            "ATOM_DELTA" to 0x00000008,
            // Aliases semânticos (apontam para os mesmos bits acima)
            "PERM_READ"  to ATOM_ALPHA,
            "PERM_WRITE" to ATOM_BETA,
            "HAS_VIDEO"  to ATOM_ALPHA,
            "HAS_PDF"    to ATOM_BETA
        )
    }
}