package com.alencar.alseth.core

import com.alencar.alseth.config.DimensionSchema

/**
 * SchemaValidator: O Auditor da Lógica.
 *
 * Responsável por garantir que a "física" do universo configurado pelo cliente faça sentido.
 * Ele detecta colisões de bits que poderiam causar bugs de segurança silenciosos.
 */
object SchemaValidator {

    /**
     * Valida um esquema completo. Lança exceção se encontrar problemas.
     * Deve ser chamado na inicialização do sistema.
     */
    fun validate(schema: DimensionSchema) {
        val contexts = schema.getContexts()
        val atoms = schema.getAtoms()

        validateContextOverlaps(contexts)
        // Nota: Átomos podem se sobrepor intencionalmente (Aliases), então somos menos rígidos,
        // mas poderíamos adicionar avisos (warnings) aqui futuramente.
    }

    /**
     * Verifica se dois contextos diferentes estão usando bits que se tocam.
     * No modelo Alseth, contextos devem ser isolados (ex: High-Order Bits).
     * Se "AUTH" for 0x1000 e "MEDIA" for 0x1100, eles colidem no bit 0x1000.
     */
    private fun validateContextOverlaps(contexts: Map<String, Int>) {
        val entries = contexts.entries.toList()

        for (i in entries.indices) {
            for (j in i + 1 until entries.size) {
                val (name1, mask1) = entries[i]
                val (name2, mask2) = entries[j]

                // Ignora a dimensão GLOBAL (0x0), pois ela é base para tudo
                if (mask1 == 0 || mask2 == 0) continue

                // Verifica intersecção bit a bit
                val intersection = mask1 and mask2
                
                if (intersection != 0) {
                    throw IllegalArgumentException(
                        "ALSETH CRITICAL ERROR: Colisão de Dimensões detectada!\n" +
                        "As dimensões '$name1' e '$name2' compartilham bits.\n" +
                        "Mask '$name1': ${binaryString(mask1)}\n" +
                        "Mask '$name2': ${binaryString(mask2)}\n" +
                        "Intersecção:   ${binaryString(intersection)}\n" +
                        "Isso quebraria o isolamento de contexto."
                    )
                }
            }
        }
    }

    private fun binaryString(value: Int): String {
        return "0b" + Integer.toBinaryString(value).padStart(32, '0')
    }
}