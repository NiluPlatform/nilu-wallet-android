plugins {
    id("library-plugin")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(Dependencies.Hilt.hilt)
    implementation(Dependencies.AndroidX.Hilt.common)
    implementation(Dependencies.AndroidX.Hilt.lifecycleViewModel)
    kapt(Dependencies.Hilt.compiler)
    kapt(Dependencies.AndroidX.Hilt.compiler)
}
