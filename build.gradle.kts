buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Kotlin.gradlePlugin)
        classpath(Dependencies.androidGradlePlugin)
        classpath(Dependencies.Hilt.gradlePlugin)
    }
}
group = Config.Android.applicationId
version = Config.Android.versionName

repositories {
    mavenCentral()
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
