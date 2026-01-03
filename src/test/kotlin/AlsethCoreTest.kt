package com.alencar.alseth

import com.alencar.alseth.config.Dimensions
import com.alencar.alseth.domain.AlsethEntity
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class AlsethCoreTest {

    @Test
    fun `Engine deve permitir acesso se a Dimensao e o Atomo baterem`() {
        // Cenário: Álvaro cria uma entidade
        val entity = AlsethEntity("id_01", "Alvaro")
        
        // Ação: Dá permissão de WRITE no contexto de AUTH
        entity.grant(Dimensions.AUTH, Dimensions.PERM_WRITE)

        // Verificação:
        // 1. Deve poder escrever em Auth
        assertTrue(entity.can(Dimensions.AUTH, Dimensions.PERM_WRITE), "Deveria ter permissão de escrita em Auth")
        
        // 2. NÃO deve poder ler em Auth (não demos essa permissão)
        assertFalse(entity.can(Dimensions.AUTH, Dimensions.PERM_READ), "Não deveria ter permissão de leitura")
    }

    @Test
    fun `Engine deve isolar contextos diferentes (Bitmask Isolation)`() {
        val entity = AlsethEntity("id_02", "ContextUser")
        
        // Dá o bit 'ATOM_ALPHA' (1) no contexto de MIDIA (Video)
        entity.grant(Dimensions.MEDIA, Dimensions.ATOM_ALPHA) 

        // Verifica se vazou para o contexto SOCIAL
        // Se a lógica estiver ruim, ele acharia o bit 1 lá também.
        assertFalse(entity.can(Dimensions.SOCIAL, Dimensions.ATOM_ALPHA), "O bit de Media não pode vazar para Social")
        
        // Verifica se funciona onde deve
        assertTrue(entity.can(Dimensions.MEDIA, Dimensions.ATOM_ALPHA), "Deveria funcionar em Media")
    }

    @Test
    fun `Engine deve revogar dimensoes inteiras (Strip)`() {
        val entity = AlsethEntity("id_03", "RevokedUser")
        
        // Dá acesso total em Auth
        entity.grant(Dimensions.AUTH, Dimensions.PERM_WRITE)
        assertTrue(entity.can(Dimensions.AUTH, Dimensions.PERM_WRITE))

        // Revoga a dimensão Auth
        entity.revokeDimension(Dimensions.AUTH)

        // Tenta acessar de novo
        assertFalse(entity.can(Dimensions.AUTH, Dimensions.PERM_WRITE), "Acesso deveria ter sido revogado")
    }
}