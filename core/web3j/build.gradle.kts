plugins {
    id("library-plugin")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":data"))
    api(project(":domain"))

    implementation(Dependencies.spongyCastle)
    implementation(Dependencies.web3j)
    implementation(Dependencies.AndroidX.collection)
    implementation(Dependencies.Google.protocolBuffers)
    implementation(Dependencies.KotlinX.coroutinesCore)
    implementation(Dependencies.OkHttp.okHttp)
    implementation(Dependencies.OkHttp.loggingInterceptor)

    implementation(Dependencies.Google.Hilt.hilt)
    kapt(Dependencies.Google.Hilt.compiler)
}
