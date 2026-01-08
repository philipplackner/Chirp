package com.plcoding.chirp.convention

import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures Android target for APPLICATION modules using com.android.application plugin.
 * Uses the traditional androidTarget {} DSL.
 */
internal fun Project.configureAndroidTarget() {
    extensions.configure<KotlinMultiplatformExtension> {
        androidTarget {
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }
}

/**
 * Configures Android target for LIBRARY modules using com.android.kotlin.multiplatform.library plugin.
 * Uses the new androidLibrary {} DSL.
 */
internal fun Project.configureAndroidLibraryTarget() {
    extensions.configure<KotlinMultiplatformExtension> {
        androidLibrary {
            namespace = this@configureAndroidLibraryTarget.pathToPackageName()
            compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()
            minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()

            // Enable Android resources (replaces experimental property)
            androidResources {
                enable = true
            }

            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }

    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("android-desugarJdkLibs").get())
    }
}