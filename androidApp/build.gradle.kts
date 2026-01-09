plugins {
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.google.services)
}

dependencies {
    implementation(projects.composeApp)
    implementation(libs.koin.android)
    implementation(libs.core.splashscreen)
    implementation(libs.core.ktx)
    implementation(libs.androidx.activity.compose)
}
