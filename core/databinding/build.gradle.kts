plugins {
    id("library-plugin")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":core:ui:view"))

    api(Dependencies.Dagger.dagger)

    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)
}
