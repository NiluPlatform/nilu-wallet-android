plugins {
    id("library-plugin")
    id("kotlin-android-extensions")
}

@Suppress("UnstableApiUsage")
android {
    androidExtensions {
        isExperimental = true
    }
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
