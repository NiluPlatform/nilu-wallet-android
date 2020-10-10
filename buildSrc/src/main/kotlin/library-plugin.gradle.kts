plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}

android {
    compileSdkVersion(Config.Android.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Config.Android.minSdkVersion)
        targetSdkVersion(Config.Android.targetSdkVersion)
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        packagingOptions {
            exclude(".readme")
            exclude("LICENSE.txt")
            exclude("META-INF/LICENSE.txt")
            exclude("META-INF/MANIFEST.MF")
            exclude("META-INF/NOTICE.txt")
            exclude("META-INF/maven/com.google.guava/guava/pom.properties")
            exclude("META-INF/maven/com.google.guava/guava/pom.xml")
            exclude("META-INF/rxjava.properties")
            exclude("META-INF/*.kotlin_module")
        }
    }
}

dependencies {

}
