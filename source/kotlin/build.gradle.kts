plugins {
    kotlin("multiplatform") version "2.4.20"
    `maven-publish`
    id("org.jetbrains.dokka") version "2.2.0"
}

group = "io.github.cosinekitty"
version = "2.1.19"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)

    jvm {
        compilations.named("main") {
            compileTaskProvider.configure {
                compilerOptions {
                    allWarningsAsErrors = true
                }
            }
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js {
        nodejs()
        browser()
    }

    wasmJs {
        nodejs()
        browser()
    }

    linuxArm64()
    linuxX64()
    macosArm64()
    mingwX64()

    iosArm64()
    iosSimulatorArm64()
    tvosArm64()
    tvosSimulatorArm64()
    watchosArm64()
    watchosDeviceArm64()
    watchosSimulatorArm64()

    sourceSets {
        getByName("jvmTest") {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
                implementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
            }
        }
    }
}

val sourceJar = tasks.register<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets["commonMain"].kotlin)
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        if (name == "jvm") {
            artifact(sourceJar)
        }
    }
}
