buildscript {
    val kotlin_version by extra("1.4.10")
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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
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
