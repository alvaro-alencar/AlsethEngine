package com.alencar.alseth.api

import com.alencar.alseth.config.DimensionSchema
import com.alencar.alseth.domain.AlsethEntity

/**
 * ManagedEntity (The Fluent Wrapper)
 * * Envolve a entidade crua para permitir comandos legíveis.
 * Faz a tradução de "AUTH" -> 0x10000000 em tempo real.
 */
class ManagedEntity(
    private val core: AlsethEntity,
    private val schema: DimensionSchema
) {
    // Caches locais para evitar chamadas repetidas ao Schema (Otimização leve)
    private val contextMap = schema.getContexts()
    private val atomMap = schema.getAtoms()

    /**
     * Concede uma permissão usando nomes legíveis.
     * Ex: user.grant("AUTH", "PERM_WRITE")
     */
    fun grant(contextName: String, permissionName: String): ManagedEntity {
        val dim = resolveContext(contextName)
        val atom = resolveAtom(permissionName)
        
        core.grant(dim, atom)
        return this // Permite encadeamento (Fluent Interface)
    }

    /**
     * Verifica permissão.
     * Ex: if (user.can("AUTH", "PERM_WRITE"))
     */
    fun can(contextName: String, permissionName: String): Boolean {
        val dim = resolveContext(contextName)
        val atom = resolveAtom(permissionName)
        
        return core.can(dim, atom)
    }

    /**
     * Revoga uma dimensão inteira pelo nome.
     */
    fun revoke(contextName: String): ManagedEntity {
        val dim = resolveContext(contextName)
        core.revokeDimension(dim)
        return this
    }

    /**
     * Acesso ao objeto original para debug ou persistência.
     */
    fun unwrap(): AlsethEntity = core

    /**
     * Atalho para o debug visual.
     */
    fun inspect(): String = core.debugState(schema)

    // --- Helpers de Resolução (Lançam erro amigável se o dev errar o nome) ---

    private fun resolveContext(name: String): Int {
        return contextMap[name] 
            ?: throw IllegalArgumentException("Dimensão desconhecida: '$name'. Verifique seu Schema.")
    }

    private fun resolveAtom(name: String): Int {
        return atomMap[name] 
            ?: throw IllegalArgumentException("Permissão/Átomo desconhecido: '$name'. Verifique seu Schema.")
    }
}