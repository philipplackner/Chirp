package com.plcoding.chirp.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureAndroidLibraryTarget() {
    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("android-desugarJdkLibs").get())
    }
}