plugins {
    id("library-plugin")
}

dependencies {
    implementation(project(":core:base"))
    api(project(":data"))
    api(project(":domain"))

    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)
}
