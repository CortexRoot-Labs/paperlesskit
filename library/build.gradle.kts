import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    id("maven-publish")
}

group = "br.com.thiagoodev"
version = "1.0.0"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.opencv)
            }
        }
    }
}

android {
    namespace = "br.com.thiagoodev.paperlesskit"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

publishing {
    repositories {
        maven {
            name = "PaperlessKit"
            url = uri("https://maven.pkg.github.com/cthiagoodev/paperlesskit")
            credentials {
                username = project.findProperty("GITHUB_USERNAME") as String?
                    ?: System.getenv("GITHUB_USERNAME")
                    ?: throw Exception("Github Username environment variable not found")
                password = project.findProperty("GITHUB_TOKEN") as String?
                    ?: System.getenv("GITHUB_TOKEN")
                    ?: throw Exception("Github Token environment variable not found")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["kotlin"])
        }
    }
}
