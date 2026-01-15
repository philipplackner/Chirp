import com.plcoding.chirp.convention.applyHierarchyTemplate
import com.plcoding.chirp.convention.configureAndroidLibraryTarget
import com.plcoding.chirp.convention.configureDesktopTarget
import com.plcoding.chirp.convention.configureIosTargets
import com.plcoding.chirp.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for Compose Multiplatform "application" modules.
 *
 * AGP 9.0 Compatibility:
 * This plugin now uses com.android.kotlin.multiplatform.library instead of com.android.application.
 * The actual Android application entry point (MainActivity, Application class) should be in a
 * separate :androidApp module that depends on the module using this plugin.
 */
class CmpApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            configureAndroidLibraryTarget()
            configureIosTargets()
            configureDesktopTarget()

            extensions.configure<KotlinMultiplatformExtension> {
                applyHierarchyTemplate()
            }

            dependencies {
                // Single-variant model: use androidMainImplementation instead of debugImplementation
                "androidMainImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
            }
        }
    }
}