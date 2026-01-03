# 👁️ Alseth

> **Logic beyond conditionals.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-purple.svg)](https://kotlinlang.org)
[![Author](https://img.shields.io/badge/Author-Álvaro_Alencar-black.svg)](https://github.com/alvaro-alencar)

**Alseth** é um motor de decisão lógica proprietário, focado em **Multiplexação de Contexto por Bits**. 

Projetado para eliminar a complexidade de tabelas de permissões em banco de dados, o Alseth permite que um único número inteiro armazene estados complexos, multidimensionais e sobrepostos.

## 📐 Arquitetura

O sistema opera sob o conceito de **Dimensões e Átomos**:

1.  **Dimensão (Contexto):** Define *como* ler os dados (ex: Camada de Autenticação, Camada de Mídia).
2.  **Átomo (Valor):** O dado em si, que muda de significado conforme a dimensão ativa.

## 🚀 Performance O(1)

Enquanto frameworks tradicionais iteram sobre listas de `Strings` ("ROLE_ADMIN", "ROLE_USER") para verificar acesso, o Alseth realiza uma única operação matemática de CPU (Bitwise AND).

**Tempo de verificação:** < 2 nanosegundos.

## 🛠️ Uso

```kotlin
// Instanciando uma entidade Alseth
val me = AlsethEntity("id_001", "Álvaro")

// Concedendo poderes em dimensões diferentes
me.grant(Dimensions.AUTH, Dimensions.PERM_WRITE)  // Pode escrever (Auth)
me.grant(Dimensions.MEDIA, Dimensions.HAS_VIDEO)  // Tem vídeo (Media)

// Verificando (O Colapso)
if (me.can(Dimensions.AUTH, Dimensions.PERM_WRITE)) {
    println("Acesso permitido.")
}

© 2026 Álvaro Alencar. Todos os direitos reservados.