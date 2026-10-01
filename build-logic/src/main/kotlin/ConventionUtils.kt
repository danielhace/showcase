import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

private const val COMPILE_SDK = 37
private const val MIN_SDK = 32

/**
 * AGP 9's built-in Kotlin support (see https://kotl.in/gradle/agp-built-in-kotlin) means the
 * `org.jetbrains.kotlin.android` plugin is no longer applied — AGP derives the Kotlin JVM target
 * from [CommonExtension.compileOptions] directly, so no separate Kotlin extension config exists.
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension,
) {
    commonExtension.apply {
        compileSdk = COMPILE_SDK

        defaultConfig.minSdk = MIN_SDK

        compileOptions.apply {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
}
