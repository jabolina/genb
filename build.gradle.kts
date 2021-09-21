plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.allopen") version "1.5.10"
    kotlin("plugin.noarg") version "1.5.10"
    kotlin("kapt") version "1.5.10"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    version = "0.0.1"
    group = "com.jabolina"

    apply(plugin = "kotlin")

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))
    }

    kotlin {
        target {
            compilations.all {
                kotlinOptions {
                    allWarningsAsErrors = true
                    jvmTarget = "11"
                }
            }
        }
    }
}
