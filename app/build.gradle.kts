plugins {
    id("danielshowcase.android.application")
    id("danielshowcase.compose")
    id("danielshowcase.koin")
}

android {
    namespace = "com.danielhc.showcase"

    defaultConfig {
        applicationId = "com.danielhc.showcase"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:presentation"))
    implementation(project(":feature:home:presentation"))
    implementation(project(":feature:facedetection:presentation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
