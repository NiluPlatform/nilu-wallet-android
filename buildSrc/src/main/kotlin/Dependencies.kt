object Config {
    object Android {
        const val minSdkVersion = 24
        const val targetSdkVersion = 30
        const val compileSdkVersion = 30
        const val applicationId = "tech.nilu.wallet.reboot"
        const val versionCode = 21
        const val versionName = "1.0.0"
    }
}

object Dependencies {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.0"
    const val web3j = "org.web3j:core:4.6.0-android"

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-alpha02"
        const val collection = "androidx.collection:collection-ktx:1.1.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.1"
        const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha03"

        object Fragment {
            private const val version = "1.3.0-beta01"
            const val fragment = "androidx.fragment:fragment:$version"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
        }

        object Hilt {
            private const val version = "1.0.0-alpha02"
            const val common = "androidx.hilt:hilt-common:$version"
            const val compiler = "androidx.hilt:hilt-compiler:$version"
            const val lifecycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$version"
        }

        object Lifecycle {
            private const val version = "2.3.0-beta01"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }

    object Dagger {
        private const val version = "2.29.1"
        const val dagger = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
    }

    object Hilt {
        private const val version = "2.29.1-alpha"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    }

    object Kotlin {
        private const val version = "1.4.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$version"
        const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    }

    object KotlinX {
        private const val coroutinesVersion = "1.4.1"
        private const val serializationVersion = "1.0.1"
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        const val coroutinesRx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$coroutinesVersion"
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion"
    }

    object MDC {
        const val material = "com.google.android.material:material:1.3.0-alpha02"
    }

    object ReactiveX {
        const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.20"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
        const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    }

    object Test
}
