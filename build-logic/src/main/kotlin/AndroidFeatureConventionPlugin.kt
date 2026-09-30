import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("danielshowcase.android.library")
            pluginManager.apply("danielshowcase.compose")
            pluginManager.apply("danielshowcase.koin")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":core:design-system"))
                add("implementation", project(":core:presentation"))
                add("implementation", libs.findLibrary("androidx-navigation-compose").get())
            }
        }
    }
}
