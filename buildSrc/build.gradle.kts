plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.1.1")
    implementation("com.android.tools.build:gradle-api:4.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.20")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.29.1-alpha")
}
