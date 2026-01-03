plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.alencar.alseth"
version = "1.0-SNAPSHOT"

repositories {
    // --- A CURA DO 403 ---
    // Adicionamos o repositório do Google primeiro. 
    // Ele serve as libs do Kotlin e é mais amigável com o GitHub Actions.
    google()
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