plugins {
    id("library-plugin")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":shared:explorer"))
    api(project(":domain"))

    implementation(Dependencies.KotlinX.coroutinesCore)

    implementation(Dependencies.Google.Hilt.hilt)
    kapt(Dependencies.Google.Hilt.compiler)
}
