plugins {
    id("library-plugin")
}

dependencies {
    api(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.Fragment.fragment)
    implementation(Dependencies.AndroidX.Fragment.fragmentKtx)

    api(Dependencies.Dagger.dagger)

    implementation(Dependencies.Hilt.hilt)

    api(Dependencies.MDC.material)
}
