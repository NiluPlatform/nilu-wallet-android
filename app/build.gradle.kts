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
    implementation(project(":shared"))
    implementation(project(":feature:main"))

    implementation(Config.Libs.appcompat)
    implementation(Config.Libs.constraintlayout)
    implementation(Config.Libs.coreKtx)
    implementation(Config.Libs.material)
    implementation(Config.Libs.hilt)
    implementation(Config.Libs.web3j)
    implementation(Config.Libs.rxJava)
    implementation(Config.Libs.rxAndroid)
    implementation(Config.Libs.rxKotlin)

    kapt(Config.Libs.hiltCompiler)
}
