plugins {
    id("library-plugin")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:thread"))
    api(project(":data"))
    api(project(":data-api"))
    api(project(":data-web3j"))
    api(project(":domain"))

    api(Dependencies.AndroidX.Room.runtime)
    api(Dependencies.AndroidX.Room.ktx)
    kapt(Dependencies.AndroidX.Room.compiler)

    implementation(Dependencies.Google.Hilt.hilt)
    kapt(Dependencies.Google.Hilt.compiler)
}
