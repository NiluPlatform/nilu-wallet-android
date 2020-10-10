plugins {
    id("library-plugin")
    id("kotlin-android-extensions")
}

android {
    androidExtensions {
        isExperimental = true
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(Config.Libs.appcompat)
    implementation(Config.Libs.constraintlayout)
    implementation(Config.Libs.coreKtx)
    implementation(Config.Libs.material)
}
