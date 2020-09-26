private const val kotlinVersion = "1.4.10"
private const val androidGradleVersion = "4.1.0-rc03"
private const val hiltGradleVersion = "2.28-alpha"

private const val materialVersion = "1.3.0-alpha02"
private const val appcompatVersion = "1.3.0-alpha02"
private const val constraintlayoutVersion = "2.0.1"
private const val hiltVersion = "2.28-alpha"

object Config {
    object BuildPlugins {
        const val androidGradle = "com.android.tools.build:gradle:$androidGradleVersion"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val hiltGradlePlugin =
            "com.google.dagger:hilt-android-gradle-plugin:$hiltGradleVersion"
    }

    object Android {
        const val minSdkVersion = 24
        const val targetSdkVersion = 30
        const val compileSdkVersion = 30
        const val applicationId = "tech.nilu.wallet"
        const val versionCode = 21
        const val versionName = "1.0.0"
    }

    object Libs {
        const val material = "com.google.android.material:material:$materialVersion"
        const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
        const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:$constraintlayoutVersion"
        const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
    }

    object TestLibs
}
