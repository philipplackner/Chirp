plugins {
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.android)
}

dependencies {
    implementation(projects.composeApp)
    implementation(libs.koin.android)
    implementation(libs.core.splashscreen)
    implementation(libs.core.ktx)
    implementation(libs.androidx.activity.compose)
}
android {
    kotlinOptions {
        jvmTarget = "17"
    }
}
