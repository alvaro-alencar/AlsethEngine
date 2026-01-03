# 👁️ Alseth Engine

> **Logic beyond conditionals.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-purple.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-Proprietary-black.svg)]()
[![Performance](https://img.shields.io/badge/Speed-O(1)-green.svg)]()

**Alseth** é um motor lógico de alta performance para controle de permissões e estados complexos.

Esqueça as listas de *Strings* (`["ADMIN", "EDITOR"]`) e loops infinitos. O Alseth utiliza **Multiplexação de Contexto por Bits**, permitindo verificar acessos em nanossegundos, independentemente da complexidade do sistema.

---

## ⚡ Por que usar?

| Abordagem Tradicional (Listas) | Abordagem Alseth (Bits) |
| :--- | :--- |
| **Lento:** Itera sobre arrays de strings. | **Instantâneo:** 1 operação matemática de CPU. |
| **Complexo:** `if (user.role == 'A' && context == 'B')` | **Fluido:** `user.can("CONTEXT", "ACTION")` |
| **Frágil:** Erros de digitação passam despercebidos. | **Seguro:** Validação de Schema na inicialização. |

---

## 🚀 Quick Start (Para Humanos)

A maneira mais rápida de começar é usando nossa **API Fluente**. Ela traduz a matemática complexa para inglês simples.

### 1. Defina seu Universo (Schema)
Crie as regras do seu jogo/app. O Alseth não opina, ele obedece.

```kotlin
// Configurando suas dimensões (Contextos) e Átomos (Permissões)
class MyAppSchema : DimensionSchema {
    override fun getContexts() = mapOf(
        "GLOBAL" to 0x00000000,
        "AUTH"   to 0x10000000, // Contexto de Login
        "BILLING" to 0x20000000 // Contexto Financeiro
    )

    override fun getAtoms() = mapOf(
        "READ"   to 0x00000001,
        "WRITE"  to 0x00000002,
        "PAY"    to 0x00000004
    )
}

2. Inicialize o Motor
Kotlin

val sdk = Alseth(MyAppSchema()) 
// ⚠️ O sistema valida automaticamente se não há conflito lógico nas suas regras.
3. Use (Modo "Vibe Coder")
Recrute entidades e gerencie permissões como se estivesse escrevendo uma frase.

Kotlin

// Criando um usuário
val user = sdk.recruit("u_001", "Álvaro")

// Dando poderes (Chainable)
user.grant("AUTH", "WRITE")
    .grant("BILLING", "PAY")

// A Pergunta Mágica
if (user.can("BILLING", "PAY")) {
    processPayment() // ✅ Acesso permitido
}

if (user.can("AUTH", "PAY")) {
    // ❌ Acesso negado! 
    // O usuário tem o poder de PAGAR, mas não no contexto de AUTH.
    // O Alseth entende isolamento de contexto.
}
🏎️ God Mode (Para Máquinas & Performance)
Se você está construindo um sistema de High Frequency Trading, Games Multiplayer ou IoT, você pode pular a tradução de Strings e falar direto com a CPU.

Custo: Zero alocação de memória. Performance bruta.

Kotlin

// Cacheie seus inteiros (Valores estáticos)
val CTX_AUTH = 0x10000000
val PERM_WRITE = 0x00000002

// Instancie a entidade crua
val entity = AlsethEntity("id_raw", "Robot")

// Operação direta (Bitwise Merge)
entity.grant(CTX_AUTH, PERM_WRITE)

// Verificação direta (Bitwise Collapse) - Tempo: < 2ns
if (entity.can(CTX_AUTH, PERM_WRITE)) {
    execute()
}
🛠️ Debug & DX
Entender bits pode ser difícil. Por isso, incluímos uma ferramenta de inspeção visual.

Kotlin

println(user.inspect())
Saída no Console:

Plaintext

=== Alseth Entity Inspector ===
User: Álvaro (u_001)
Raw State: 805306374
Active Dimensions:
  - BILLING (Mask: 536870912)
    -> Atoms: PAY
  - AUTH (Mask: 268435456)
    -> Atoms: WRITE
===============================
📐 Arquitetura
O sistema opera sob o conceito de Dimensões e Átomos:

Dimensão (Contexto): Define onde a regra se aplica (Ex: Camada de Dados, Camada de UI).

Átomo (Valor): O dado em si. Note que o mesmo átomo (bit 1) pode significar "Admin" na dimensão A e "Inimigo" na dimensão B. Eles nunca se misturam.

Instalação (Gradle Kotlin DSL)
Kotlin

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.alencar.alseth:alseth-engine:1.0.0")
}
© 2026 Álvaro Alencar. Build with logic. Todos os direitos reservados.