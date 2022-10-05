plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
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

    implementation(rootProject.extra.get("coreKtx") as String)
    implementation(rootProject.extra.get("activityCompose") as String)

    implementation(rootProject.extra.get("accompanistInsets") as String)
    implementation(rootProject.extra.get("accompanistSystemUiController") as String)

    implementation(rootProject.extra.get("machine") as String)

    implementation(rootProject.extra.get("dagger") as String)
    kapt(rootProject.extra.get("daggerCompiler") as String)

    implementation(rootProject.extra.get("dataStorePreferences") as String)
    implementation(rootProject.extra.get("dataStore") as String)
    implementation(rootProject.extra.get("protobufJavalite") as String)
    implementation(rootProject.extra.get("preferenceKtx") as String)

    implementation(rootProject.extra.get("kotlinxSerializationJson") as String)

    implementation(rootProject.extra.get("ktorClientCio") as String)
    implementation(rootProject.extra.get("ktorClientAndroid") as String)
    implementation(rootProject.extra.get("ktorClientAuth") as String)
    implementation(rootProject.extra.get("ktorClientSerialization") as String)
    implementation(rootProject.extra.get("ktorClientCore") as String)
    implementation(rootProject.extra.get("ktorClientWebsocket") as String)
    implementation(rootProject.extra.get("ktorClientLoggingJvm") as String)

    (rootProject.extra.get("compose") as List<*>).forEach { compose ->
        implementation(compose as String)
    }
    (rootProject.extra.get("coil") as List<*>).forEach { compose ->
        implementation(compose as String)
    }

    (rootProject.extra.get("sqlDelight") as List<*>).forEach { compose ->
        implementation(compose as String)
    }

    (rootProject.extra.get("presenter") as List<*>).forEach { compose ->
        implementation(compose as String)
    }


}