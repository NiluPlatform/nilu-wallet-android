buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Kotlin.gradlePlugin)
        classpath(Dependencies.androidGradlePlugin)
        classpath(Dependencies.Google.Hilt.gradlePlugin)
        classpath(Dependencies.Kotlin.serialization)
        classpath(Dependencies.SQLDelight.gradlePlugin)
    }
}
group = Config.Android.applicationId
version = Config.Android.versionName

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
