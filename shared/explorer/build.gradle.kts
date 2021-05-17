import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
}

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "explorer"
            }
        }
    }
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                useExperimentalAnnotation("kotlinx.serialization.InternalSerializationApi")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Kodein.core)
                implementation(Dependencies.KotlinX.coroutinesCore)
                implementation(Dependencies.KotlinX.serializationCore)
                implementation(Dependencies.Ktor.core)
                implementation(Dependencies.Ktor.serialization)
                implementation(Dependencies.Ktor.logging)
                implementation(Dependencies.SQLDelight.runtime)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(project(":core:base"))
                api(project(":domain"))

                api(Dependencies.AndroidX.coreKtx)
                implementation(Dependencies.KotlinX.coroutinesAndroid)
                implementation(Dependencies.Ktor.android)
                implementation(Dependencies.Ktor.okhttp)
                implementation(Dependencies.OkHttp.loggingInterceptor)
                implementation(Dependencies.SQLDelight.androidDriver)
                implementation(Dependencies.SQLDelight.coroutines)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Dependencies.KotlinX.coroutinesCore)
                implementation(Dependencies.Ktor.ios)
                implementation(Dependencies.SQLDelight.iosDriver)
            }
        }
    }
}

android {
    compileSdkVersion(Config.Android.compileSdkVersion)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].java.srcDirs("src/androidMain/kotlin")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    defaultConfig {
        minSdkVersion(Config.Android.minSdkVersion)
        targetSdkVersion(Config.Android.targetSdkVersion)
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
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
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

sqldelight {
    database("NiluDatabase") {
        packageName = "tech.nilu.explorer.database"
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)
