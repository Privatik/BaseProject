plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from =  rootProject.file("dependencies.gradle.kts"))

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "io.my.baseproject"
        minSdk = 24
        targetSdk = 33
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
        kotlinCompilerExtensionVersion = rootProject.extra.get("composeCompileVersion") as String
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":auth:ui"))
    implementation(project(":routing"))
    implementation(project(":core"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))

    implementation(rootProject.extra.get("coreKtx") as String)
    implementation(rootProject.extra.get("activityCompose") as String)

    implementation(rootProject.extra.get("dagger") as String)
    kapt(rootProject.extra.get("daggerCompiler") as String)

}