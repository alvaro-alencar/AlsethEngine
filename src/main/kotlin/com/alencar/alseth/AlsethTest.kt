package com.alencar.alseth

import com.alencar.alseth.config.Dimensions
import com.alencar.alseth.domain.AlsethEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AlsethTest {

    @Test
    fun `Deve permitir acesso simples em um contexto`() {
        val user = AlsethEntity("u1", "Tester")
        
        // Dá permissão de Leitura no contexto AUTH
        user.grant(Dimensions.AUTH, Dimensions.ATOM_ALPHA)

        // Verifica
        assertTrue(user.can(Dimensions.AUTH, Dimensions.ATOM_ALPHA), "Deveria ler em Auth")
        assertFalse(user.can(Dimensions.AUTH, Dimensions.ATOM_BETA), "Não deveria escrever em Auth")
    }

    @Test
    fun `Deve isolar decisoes baseadas na existencia do contexto`() {
        val user = AlsethEntity("u2", "QuantumUser")
        
        // O usuário tem o BIT 1 ligado (ALPHA), mas SÓ no contexto de MÍDIA.
        user.grant(Dimensions.MEDIA, Dimensions.ATOM_ALPHA) // Digamos que ALPHA seja "Ver Vídeo"

        // Verifica Mídia
        assertTrue(user.can(Dimensions.MEDIA, Dimensions.ATOM_ALPHA), "Pode ver vídeo")

        // Tenta verificar se ele tem permissão de "Ler" (ALPHA) no contexto AUTH.
        // Como ele NÃO TEM o contexto AUTH ativado, o bit ALPHA deve ser ignorado.
        assertFalse(user.can(Dimensions.AUTH, Dimensions.ATOM_ALPHA), 
            "O Bit Alpha não deve vazar para Auth se o contexto Auth não existe")
    }

    @Test
    fun `Deve remover camadas de realidade (Strip)`() {
        val user = AlsethEntity("u3", "Stripper")
        
        // Adiciona Auth e Media
        user.grant(Dimensions.AUTH, Dimensions.ATOM_ALPHA)
        user.grant(Dimensions.MEDIA, Dimensions.ATOM_BETA)

        assertTrue(user.can(Dimensions.MEDIA, Dimensions.ATOM_BETA))

        // Remove a dimensão de Mídia
        user.revokeDimension(Dimensions.MEDIA)

        assertFalse(user.can(Dimensions.MEDIA, Dimensions.ATOM_BETA), "A dimensão Media devia ter sumido")
        assertTrue(user.can(Dimensions.AUTH, Dimensions.ATOM_ALPHA), "Auth deve permanecer intacto")
    }
}