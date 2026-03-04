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
                // Core Compose dependencies
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-runtime").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-foundation").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material3").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-ui").get())

                // CMP 1.10.0+: Resources and preview tooling are now separate modules
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-resources").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-ui-tooling-preview").get())

                // Single-variant model: use androidMainImplementation instead of debugImplementation
                "androidMainImplementation"(libs.findLibrary("jetbrains-compose-ui-tooling").get())
            }
        }
    }
}