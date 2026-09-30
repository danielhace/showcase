plugins {
    `kotlin-dsl`
}

group = "com.danielhc.showcase.buildlogic"

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.compose.compiler.gradlePlugin)
    implementation(libs.kotlin.serialization.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "danielshowcase.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "danielshowcase.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "danielshowcase.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("compose") {
            id = "danielshowcase.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("koin") {
            id = "danielshowcase.koin"
            implementationClass = "KoinConventionPlugin"
        }
        register("kotlinxSerialization") {
            id = "danielshowcase.kotlinx.serialization"
            implementationClass = "KotlinxSerializationConventionPlugin"
        }
    }
}
