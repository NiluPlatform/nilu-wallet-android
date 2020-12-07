pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android" || requested.id.name == "kotlin-android-extensions") {
                useModule("com.android.tools.build:gradle:4.0.1")
            }
        }
    }
}
rootProject.name = "NiluWallet"

include(":app")
include(
    ":core:base",
    ":core:databinding",
    ":core:thread",
    ":core:ui"
)
include(
    ":data",
    ":data-android",
    ":data-web3j"
)
include(":domain")
include(
    ":feature:main"
)
include(
    ":shared:explorer"
)
