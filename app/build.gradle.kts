plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
}
group = Config.Android.applicationId
version = Config.Android.versionName

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
dependencies {
    implementation(project(":shared"))
    implementation(Config.Libs.material)
    implementation(Config.Libs.appcompat)
    implementation(Config.Libs.constraintlayout)
    implementation(Config.Libs.hilt)

    kapt(Config.Libs.hiltCompiler)
}
android {
    compileSdkVersion(Config.Android.compileSdkVersion)
    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdkVersion(Config.Android.minSdkVersion)
        targetSdkVersion(Config.Android.targetSdkVersion)
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }
    buildFeatures {
        dataBinding = true
    }
}
