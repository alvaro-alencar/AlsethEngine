plugins {
    // Garante suporte ao Kotlin JVM
    kotlin("jvm") version "2.0.0" 
    application
}

group = "com.alencar.alseth"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // A biblioteca de testes oficial do Kotlin
    testImplementation(kotlin("test"))
    
    // JUnit 5 (O motor de execução dos testes)
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    // Isso diz ao Gradle: "Use o JUnit Platform para rodar os testes"
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17) // Garante compatibilidade com Java 17
}

application {
    mainClass.set("com.alencar.alseth.MainKt") // Se tiver um main, senão pode ignorar
}