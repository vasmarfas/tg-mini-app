import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")

    id("convention.publication")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

group = "io.github.kirillNay"
version = "1.2.0"

repositories {
    mavenCentral()
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

kotlin {
    js(IR) {
        moduleName = "mini-app"
        browser()
        binaries.executable()
        nodejs {
            version = "16.20.2" // Совместимая версия для GLIBC 2.23 (Ubuntu 16.04)

        }
    }

    sourceSets {
        jsMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.ui)
        }
    }
}

