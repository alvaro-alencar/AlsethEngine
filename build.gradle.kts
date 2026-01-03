plugins {
    kotlin("jvm") version "2.0.0"
    // Removemos o plugin 'application' porque isso é uma biblioteca
}

group = "com.alencar.alseth"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Implementação padrão do Kotlin para testes
    testImplementation(kotlin("test"))
    
    // O motor JUnit 5 (Isso permite usar @Test)
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    // Diz ao Gradle para usar o JUnit
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17) // Garante compatibilidade com o GitHub Actions
}