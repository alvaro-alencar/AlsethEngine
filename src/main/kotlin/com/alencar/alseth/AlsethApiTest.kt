package com.alencar.alseth

import com.alencar.alseth.api.Alseth
import com.alencar.alseth.config.DefaultSchema
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AlsethApiTest {

    @Test
    fun `High Level API deve permitir fluxo fluente legivel`() {
        // 1. Setup: O cliente inicializa o SDK com suas regras
        val sdk = Alseth(DefaultSchema())

        // 2. Recrutamento: Criação do usuário gerenciado
        val user = sdk.recruit("u_100", "FluentUser")

        // 3. Ação: Interface Fluente (Chainable)
        user.grant("AUTH", "PERM_WRITE")
            .grant("MEDIA", "HAS_VIDEO")

        // 4. Verificação legível
        assertTrue(user.can("AUTH", "PERM_WRITE"), "Deveria escrever em Auth")
        assertTrue(user.can("MEDIA", "HAS_VIDEO"), "Deveria ver vídeo")
        
        // 5. Teste de isolamento
        assertFalse(user.can("SOCIAL", "HAS_VIDEO"), "Não deveria ter vídeo no contexto Social")
    }

    @Test
    fun `API deve proteger o desenvolvedor de erros de digitacao`() {
        val sdk = Alseth(DefaultSchema())
        val user = sdk.recruit("u_err", "ErrorUser")

        // Tenta usar uma dimensão que não existe ("FINANCEIRO")
        val exception = assertThrows<IllegalArgumentException> {
            user.grant("FINANCEIRO", "PERM_READ")
        }

        // Verifica se a mensagem de erro é educativa
        assertTrue(exception.message!!.contains("Dimensão desconhecida"), "Deve avisar sobre o erro de nome")
    }
}