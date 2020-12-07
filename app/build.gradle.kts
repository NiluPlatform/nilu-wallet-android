plugins {
    id("app-plugin")
    id("dagger.hilt.android.plugin")
}

group = Config.Android.applicationId
version = Config.Android.versionName

android {
    defaultConfig {
        applicationId = Config.Android.applicationId
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packagingOptions {
        packagingOptions {
            exclude("META-INF/AL2.0")
            exclude("META-INF/LGPL2.1")
        }
    }
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:databinding"))
    implementation(project(":core:thread"))
    implementation(project(":core:ui"))
    implementation(project(":data"))
    implementation(project(":data-android"))
    implementation(project(":data-web3j"))
    implementation(project(":domain"))
    implementation(project(":feature:main"))
    implementation(project(":explorer"))

    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.Fragment.fragmentKtx)

    implementation(Dependencies.Google.Hilt.hilt)
    implementation(Dependencies.AndroidX.Hilt.lifecycleViewModel)
    kapt(Dependencies.Google.Hilt.compiler)
    kapt(Dependencies.AndroidX.Hilt.compiler)

    implementation(Dependencies.MDC.material)

    implementation(Dependencies.ReactiveX.rxJava)
    implementation(Dependencies.ReactiveX.rxAndroid)
    implementation(Dependencies.ReactiveX.rxKotlin)

    implementation(Dependencies.web3j)
}
