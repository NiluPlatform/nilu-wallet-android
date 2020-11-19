plugins {
    id("library-plugin")
}

dependencies {
    api(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.Fragment.fragment)
    implementation(Dependencies.AndroidX.Fragment.fragmentKtx)

    api(Dependencies.Google.Dagger.dagger)

    implementation(Dependencies.Google.Hilt.hilt)

    api(Dependencies.MDC.material)
}
