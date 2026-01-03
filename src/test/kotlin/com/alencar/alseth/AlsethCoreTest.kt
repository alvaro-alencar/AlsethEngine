package com.alencar.alseth

import com.alencar.alseth.config.Dimensions
import com.alencar.alseth.domain.AlsethEntity
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class AlsethCoreTest {

    @Test
    fun \Engine deve permitir acesso se a Dimensao e o Atomo baterem\() {
        val entity = AlsethEntity("id_01", "Alvaro")
        entity.grant(Dimensions.AUTH, Dimensions.PERM_WRITE)
        assertTrue(entity.can(Dimensions.AUTH, Dimensions.PERM_WRITE), "Deveria ter permissão de escrita em Auth")
        assertFalse(entity.can(Dimensions.AUTH, Dimensions.PERM_READ), "Não deveria ter permissão de leitura")
    }

    @Test
    fun \Engine deve isolar contextos diferentes (Bitmask Isolation)\() {
        val entity = AlsethEntity("id_02", "ContextUser")
        entity.grant(Dimensions.MEDIA, Dimensions.ATOM_ALPHA) 
        assertFalse(entity.can(Dimensions.SOCIAL, Dimensions.ATOM_ALPHA), "O bit de Media não pode vazar para Social")
        assertTrue(entity.can(Dimensions.MEDIA, Dimensions.ATOM_ALPHA), "Deveria funcionar em Media")
    }

    @Test
    fun \Engine deve revogar dimensoes inteiras (Strip)\() {
        val entity = AlsethEntity("id_03", "RevokedUser")
        entity.grant(Dimensions.AUTH, Dimensions.PERM_WRITE)
        assertTrue(entity.can(Dimensions.AUTH, Dimensions.PERM_WRITE))
        entity.revokeDimension(Dimensions.AUTH)
        assertFalse(entity.can(Dimensions.AUTH, Dimensions.PERM_WRITE), "Acesso deveria ter sido revogado")
    }
}
