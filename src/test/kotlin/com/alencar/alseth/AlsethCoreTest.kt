package com.alencar.alseth

import com.alencar.alseth.config.DefaultSchema
import com.alencar.alseth.core.SchemaValidator
import com.alencar.alseth.domain.AlsethEntity
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

class AlsethCoreTest {

    // Simulando a inicialização do ambiente do cliente
    private val schema = DefaultSchema()
    
    // Cacheamos os inteiros para performance (como o cliente faria)
    private val CTX_AUTH = schema.getContexts()["AUTH"]!!
    private val CTX_MEDIA = schema.getContexts()["MEDIA"]!!
    private val CTX_SOCIAL = schema.getContexts()["SOCIAL"]!!
    
    private val PERM_WRITE = schema.getAtoms()["PERM_WRITE"]!!
    private val PERM_READ = schema.getAtoms()["PERM_READ"]!!
    private val ATOM_ALPHA = schema.getAtoms()["ATOM_ALPHA"]!!

    @BeforeEach
    fun setup() {
        // O Tribunal: Valida as regras antes de rodar
        SchemaValidator.validate(schema)
    }

    @Test
    fun `Engine deve permitir acesso baseado em Schema Dinamico`() {
        val entity = AlsethEntity("id_01", "DynamicUser")
        
        // Uso das variáveis dinâmicas
        entity.grant(CTX_AUTH, PERM_WRITE)

        // Debug visual nos logs
        println(entity.debugState(schema))

        assertTrue(entity.can(CTX_AUTH, PERM_WRITE), "Deveria ter permissão de escrita em Auth")
        assertFalse(entity.can(CTX_AUTH, PERM_READ), "Não deveria ter permissão de leitura")
    }

    @Test
    fun `Engine deve isolar contextos (Bitmask Isolation)`() {
        val entity = AlsethEntity("id_02", "ContextUser")
        
        entity.grant(CTX_MEDIA, ATOM_ALPHA)

        assertFalse(entity.can(CTX_SOCIAL, ATOM_ALPHA), "O bit de Media não pode vazar para Social")
        assertTrue(entity.can(CTX_MEDIA, ATOM_ALPHA), "Deveria funcionar em Media")
    }

    @Test
    fun `Engine deve revogar dimensoes (Strip)`() {
        val entity = AlsethEntity("id_03", "RevokedUser")
        
        entity.grant(CTX_AUTH, PERM_WRITE)
        assertTrue(entity.can(CTX_AUTH, PERM_WRITE))

        entity.revokeDimension(CTX_AUTH)

        assertFalse(entity.can(CTX_AUTH, PERM_WRITE), "Acesso deveria ter sido revogado")
    }
}