plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.my.core"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(rootProject.extra.get("coreKtx") as String)
    implementation(rootProject.extra.get("lifecycleRuntimeKtx") as String)

    api(rootProject.extra.get("machine") as String)

    implementation(rootProject.extra.get("presenterCommon") as String)
    implementation(rootProject.extra.get("presenterAndroid") as String)
    implementation(rootProject.extra.get("dagger") as String)
}