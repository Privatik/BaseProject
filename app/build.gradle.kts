plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

apply(from =  rootProject.file("dependencies.gradle.kts"))

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "io.my.baseproject"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra.get("composeVersion") as String
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(rootProject.extra.get("coreKtx") as String)
    implementation(rootProject.extra.get("activityCompose") as String)

    (rootProject.extra.get("compose") as List<*>).forEach { compose ->
        implementation(compose as String)
    }

}