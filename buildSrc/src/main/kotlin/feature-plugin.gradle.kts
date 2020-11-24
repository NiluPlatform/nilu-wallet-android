plugins {
    id("library-plugin")
}

@Suppress("UnstableApiUsage")
android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.coreKtx)

    implementation(Dependencies.MDC.material)
}
