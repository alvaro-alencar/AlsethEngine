package com.alencar.alseth.api

import com.alencar.alseth.config.DimensionSchema
import com.alencar.alseth.core.SchemaValidator
import com.alencar.alseth.domain.AlsethEntity

/**
 * Alseth SDK (High-Level API)
 * * O ponto de entrada principal para quem busca facilidade de uso.
 * Gerencia a validação de regras e a criação de entidades "gerenciadas".
 * * Uso:
 * val app = Alseth(MySchema())
 * val user = app.recruit("id_1", "User")
 */
class Alseth(private val schema: DimensionSchema) {

    init {
        // Na inicialização, já garantimos que as regras do cliente não têm falhas.
        // Se houver colisão de bits, o app nem sobe (Fail Fast).
        SchemaValidator.validate(schema)
    }

    /**
     * Cria uma nova entidade gerenciada pronta para uso com Strings.
     * O termo "Recruit" reforça a ideia de trazer alguém para o universo Alseth.
     */
    fun recruit(id: String, alias: String): ManagedEntity {
        // Cria a entidade crua (leve) e a envolve no wrapper (inteligente)
        val rawEntity = AlsethEntity(id, alias)
        return ManagedEntity(rawEntity, schema)
    }
}