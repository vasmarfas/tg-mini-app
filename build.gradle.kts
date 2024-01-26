plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")

    id("convention.publication")
}

group = "io.github.kirillNay"
version = "1.0.1"

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
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
            }
        }
    }
}

compose.experimental {
    web.application {}
}
