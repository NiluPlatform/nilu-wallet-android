plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependencies.Kotlin.stdlibJdk8)
    implementation(Dependencies.KotlinX.coroutinesCore)

    implementation(Dependencies.Google.Hilt.hilt)
    kapt(Dependencies.Google.Hilt.compiler)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
