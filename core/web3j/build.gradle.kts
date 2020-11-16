plugins {
    id("library-plugin")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":data"))
    api(project(":domain"))

    implementation(Dependencies.web3j)
    implementation(Dependencies.KotlinX.coroutinesCore)
    implementation(Dependencies.OkHttp.okHttp)
    implementation(Dependencies.OkHttp.loggingInterceptor)

    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)
}
