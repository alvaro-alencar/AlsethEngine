package com.alencar.alseth.core

import com.alencar.alseth.config.Dimensions

/**
 * Alseth Engine
 * O núcleo de processamento lógico.
 * * Responsabilidade: Executar operações bitwise determinísticas
 * para validar a existência de átomos dentro de dimensões.
 */
object BitmaskEngine {

    /**
     * O Colapso da Função de Onda.
     * Verifica se um 'atom' (permissão) existe dentro de um 'state' (usuário),
     * considerando a 'dimension' (contexto) atual.
     *
     * @param currentState O estado inteiro atual da entidade.
     * @param targetDimension A dimensão que estamos observando (ex: AUTH).
     * @param targetAtom O bit específico que buscamos (ex: ATOM_ALPHA).
     */
    fun collapse(currentState: Int, targetDimension: Int, targetAtom: Int): Boolean {
        // 1. Isolamento Dimensional:
        // Verificamos se o estado possui a flag da dimensão ativa OU se é dimensão Global.
        val hasDimension = (currentState and targetDimension) != 0 || targetDimension == Dimensions.GLOBAL

        // 2. Verificação Atômica:
        // Se a dimensão existe, verificamos se o bit do átomo está ligado.
        return if (hasDimension) {
            (currentState and targetAtom) != 0
        } else {
            false
        }
    }

    /**
     * Fusão de Realidade.
     * Adiciona uma nova dimensão e seus átomos ao estado existente.
     */
    fun merge(currentState: Int, dimension: Int, atoms: Int): Int {
        return currentState or dimension or atoms
    }

    /**
     * Extração de Realidade.
     * Remove completamente uma dimensão do estado (e seus átomos associados, 
     * se não estiverem protegidos por outra lógica, mas aqui é limpeza bruta).
     *
     * Útil para sanitizar objetos antes de enviá-los para o front-end.
     */
    fun strip(currentState: Int, dimension: Int): Int {
        // Inverte a máscara da dimensão e faz um AND para "desligar" apenas aquela parte
        return currentState and dimension.inv()
    }
}