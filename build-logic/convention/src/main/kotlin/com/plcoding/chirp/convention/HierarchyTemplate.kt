@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

package com.plcoding.chirp.convention

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyTemplate
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

private val hierarchyTemplate = KotlinHierarchyTemplate {
    withSourceSetTree(
        KotlinSourceSetTree.main,
        KotlinSourceSetTree.test,
    )

    common {
        withCompilations { true }

        /*
            AGP 9.0 Prep:
            jvmCommon was removed because Android belonged to both mobile and jvmCommon,
            creating overlapping source-set paths. With the new AGP KMP plugin, this ambiguity
            forces actuals to exist in all intermediate source sets, leading to compilation
            errors. Removing jvmCommon globally avoids this conflict, while modules that truly
            need Android + Desktop JVM sharing can opt in explicitly using dependsOn().
         */
        group("mobile") {
            withAndroidTarget()
            group("ios") {
                withIos()
            }
        }

        /*
            Android no longer automatically depends on intermediate source sets such as
            mobileMain or jvmCommonMain, and jvmCommonMain has been removed from the global
            hierarchy. As of Gradle 9.0, any module that needs to share code between Android
            and Desktop JVM must explicitly configure this dependency, for example by
            making androidMain depend on jvmCommonMain.
        */
        group("native") {
            withNative()

            group("apple") {
                withApple()

                group("ios") {
                    withIos()
                }

                group("macos") {
                    withMacos()
                }
            }
        }
    }
}

fun KotlinMultiplatformExtension.applyHierarchyTemplate() {
    applyHierarchyTemplate(hierarchyTemplate)
}