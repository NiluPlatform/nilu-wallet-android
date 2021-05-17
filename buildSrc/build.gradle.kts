plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.0")
    implementation("com.android.tools.build:gradle-api:4.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.29.1-alpha")
}
