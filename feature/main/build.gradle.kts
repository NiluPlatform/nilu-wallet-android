plugins {
    id("feature-plugin")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:databinding"))
    implementation(project(":core:thread"))
    implementation(project(":core:ui"))
    api(project(":domain"))

    implementation(Dependencies.AndroidX.Fragment.fragment)
    implementation(Dependencies.AndroidX.Fragment.fragmentKtx)
    implementation(Dependencies.AndroidX.Lifecycle.liveData)
    implementation(Dependencies.AndroidX.Lifecycle.viewModel)

    implementation(Dependencies.Hilt.hilt)
    implementation(Dependencies.AndroidX.Hilt.lifecycleViewModel)
    kapt(Dependencies.Hilt.compiler)
    kapt(Dependencies.AndroidX.Hilt.compiler)
}
