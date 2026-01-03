plugins {
    kotlin("jvm") version "2.0.0"
    // application <- REMOVIDO (Isso é para apps executáveis, nós somos uma lib)
}

group = "com.alencar.alseth"
version = "1.0-SNAPSHOT"

repositories {
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