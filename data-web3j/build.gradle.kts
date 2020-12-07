plugins {
    id("library-plugin")
    kotlin("plugin.serialization")
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
    implementation(Dependencies.KotlinX.coroutinesRx2)
    implementation(Dependencies.KotlinX.serializationJson)
    implementation(Dependencies.OkHttp.okHttp)
    implementation(Dependencies.OkHttp.loggingInterceptor)

    implementation(Dependencies.Google.Hilt.hilt)
    kapt(Dependencies.Google.Hilt.compiler)
}
