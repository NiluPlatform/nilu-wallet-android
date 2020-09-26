buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Config.BuildPlugins.kotlinGradlePlugin)
        classpath(Config.BuildPlugins.androidGradle)
        classpath(Config.BuildPlugins.hiltGradlePlugin)
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
