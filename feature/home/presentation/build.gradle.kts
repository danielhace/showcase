plugins {
    id("danielshowcase.android.feature")
    id("danielshowcase.kotlinx.serialization")
}

android {
    namespace = "com.danielhc.showcase.feature.home.presentation"
}

dependencies {
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.turbine)
    testImplementation(libs.assertk)
    testImplementation(libs.kotlinx.coroutines.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
