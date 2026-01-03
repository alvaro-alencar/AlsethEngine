plugins {
    // Mantemos o Kotlin 2.0.0
    kotlin("jvm") version "2.0.0"
}

group = "com.alencar.alseth"
version = "1.0-SNAPSHOT"

repositories {
    // --- A FORTALEZA (Anti-Block Strategy) ---
    
    // 1. Gradle Plugin Portal:
    // Nos logs, vimos que este endereço NÃO está bloqueado.
    // Ele espelha muitas libs essenciais, incluindo o Kotlin Stdlib.
    maven { url = uri("https://plugins.gradle.org/m2/") }
    
    // 2. Google:
    // Backup seguro para libs Android/Kotlin.
    google()
    
    // 3. Maven Central:
    // Deixamos por último. Se os de cima falharem, ele tenta aqui.
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}