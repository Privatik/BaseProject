import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
}

val prop = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

android {
    namespace = "io.my.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "KeyForRefreshToken", prop.getProperty("KEY_FOR_REFRESH_TOKEN", ""))
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
    api(project(":core:domain"))

    api(rootProject.extra.get("dataStore") as String)
    api(rootProject.extra.get("dataStorePreferences") as String)


    api(rootProject.extra.get("kotlinxSerializationJson") as String)


    api(rootProject.extra.get("ktorClientLoggingJvm") as String)
    api(rootProject.extra.get("ktorClientCore") as String)
    api(rootProject.extra.get("ktorClientSerialization") as String)
    api(rootProject.extra.get("ktorClientCio") as String)

    (rootProject.extra.get("sqlDelight") as List<*>).forEach { compose ->
        api(compose as String)
    }

    implementation(rootProject.extra.get("dagger") as String)
    kapt(rootProject.extra.get("daggerCompiler") as String)
}