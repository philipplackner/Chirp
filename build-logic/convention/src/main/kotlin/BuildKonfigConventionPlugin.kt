import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import com.plcoding.chirp.convention.pathToPackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class BuildKonfigConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.codingfeline.buildkonfig")
            }

            extensions.configure<BuildKonfigExtension> {
                packageName = target.pathToPackageName()
                defaultConfigs {
                    val apiKey = gradleLocalProperties(rootDir, rootProject.providers)
                        .getProperty("API_KEY")
                        ?: throw IllegalStateException(
                            "Missing API_KEY property in local.properties"
                        )
                    buildConfigField(FieldSpec.Type.STRING, "API_KEY", apiKey)
                }
            }
        }
    }
}