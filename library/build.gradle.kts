import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "br.com.thiagoodev"
version = "1.0.0-alpha"

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

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "paperlesskit", version.toString())

    pom {
        name = "PaperlessKit"
        description = "PaperlessKit is a multiplatform library designed to simplify " +
                "the scanning, selection, and manipulation of PDF files. It allows capturing " +
                "documents using the device's camera, selecting existing files from local " +
                "storage, and opening PDFs for viewing in an intuitive and efficient way."
        inceptionYear = "2024"
        url = "https://github.com/cthiagoodev/paperlesskit/"
        licenses {
            license {
                name = "Apache-2.0"
                url = "https://opensource.org/licenses/Apache-2.0"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "cthiagoodev"
                name = "Thiago Sousa"
                url = "https://github.com/cthiagoodev"
            }
        }
        scm {
            url = "https://github.com/cthiagoodev/paperlesskit"
            connection = "scm:git:git://github.com/cthiagoodev/paperlesskit.git"
            developerConnection = "scm:git:git@github.com:cthiagoodev/paperlesskit.git"
        }
    }
}