plugins {
    id("library-plugin")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":core:ui"))

    api(Dependencies.Dagger.dagger)

    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)
}
