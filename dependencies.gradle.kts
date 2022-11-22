//versions
val coreKtxVersion by extra { "1.9.0" }
val lifecycleRuntimeKtxVersion by extra { "2.5.1" }
val activityComposeVersion by extra { "1.6.0" }
val composeVersion by extra { "1.1.1" }
val composeCompileVersion by extra {"1.3.2" }
val accompanistVersion by extra { "0.19.0" }
val coilVersion by extra { "1.4.0" }
val sqlDelightVersion by extra { "1.5.2" }
val kotlinxSerializationJsonVersion by extra { "1.4.0" }
val ktorClientVersion by extra { "1.6.5" }
val datastoreVersion by extra { "1.0.0" }
val protobufJavaliteVersion by extra { "3.18.0" }
val preferenceKtxVersion by extra { "1.2.0" }
val daggerVersion by extra { "2.44" }
val privatikLibraryVersion by extra { "1.0.2-beta" }
val jetpackNavigationComposeVersion by extra { "2.5.2" }
val kotlinxCoroutinesCoreJvmVersion by extra { "1.6.4" }
val appyxVersion by extra { "1.0.0" }

//dependencies

//compose
val compose by extra {
    listOf(
        "androidx.compose.ui:ui:${composeVersion}",
        "androidx.compose.material:material:${composeVersion}",
        "androidx.compose.ui:ui-tooling-preview:${composeVersion}",
        "androidx.compose.ui:ui-tooling:${composeVersion}",
        "androidx.compose.ui:ui-test-manifest:${composeVersion}",
        "androidx.compose.material:material-icons-extended:${composeVersion}"
    )
}

//coil
val coil by extra {
    listOf(
        "io.coil-kt:coil-base:$coilVersion",
        "io.coil-kt:coil-compose:$coilVersion"
    )
}

//sqlDelight
val sqlDelight by extra {
    listOf(
        "com.squareup.sqldelight:android-driver:$sqlDelightVersion",
        "com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion"
    )
}

//core
val coreKtx by extra { "androidx.core:core-ktx:${coreKtxVersion}" }
val lifecycleRuntimeKtx by extra { "androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleRuntimeKtxVersion}" }
val activityCompose by extra { "androidx.activity:activity-compose:${activityComposeVersion}" }

val kotlinxCoroutinesCoreJvm by extra { "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinxCoroutinesCoreJvmVersion" }

//accompanist
val accompanistInsets by extra { "com.google.accompanist:accompanist-insets:$accompanistVersion" }
val accompanistSystemUiController by extra { "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion" }

//http client
val ktorClientCio by extra { "io.ktor:ktor-client-cio:$ktorClientVersion" }
val ktorClientAndroid by extra { "io.ktor:ktor-client-android:$ktorClientVersion" }
val ktorClientAuth by extra { "io.ktor:ktor-client-auth:$ktorClientVersion" }
val ktorClientSerialization by extra { "io.ktor:ktor-client-serialization:$ktorClientVersion" }
val ktorClientCore by extra { "io.ktor:ktor-client-core:$ktorClientVersion" }
val ktorClientWebsocket by extra { "io.ktor:ktor-client-websockets:$ktorClientVersion" }
val ktorClientLoggingJvm by extra { "io.ktor:ktor-client-logging-jvm:$ktorClientVersion" }

//serialize
val kotlinxSerializationJson by extra {"org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationJsonVersion" }

//DataStore
val dataStorePreferences by extra { "androidx.datastore:datastore-preferences:$datastoreVersion" }
val dataStore by extra { "androidx.datastore:datastore:$datastoreVersion" }
val protobufJavalite by extra { "com.google.protobuf:protobuf-javalite:$protobufJavaliteVersion" }
val preferenceKtx by extra { "androidx.preference:preference-ktx:$preferenceKtxVersion" }

//Navigation
val appyx by extra { "com.bumble.appyx:core:$appyxVersion" }

//Dagger
val dagger by extra {"com.google.dagger:dagger:$daggerVersion" }
val daggerCompiler by extra {"com.google.dagger:dagger-compiler:$daggerVersion" }

//Privatik library
val machine by extra { "io.github.privatik:machine:$privatikLibraryVersion" }
val presenterCommon by extra { "io.github.privatik:presenter-common:$privatikLibraryVersion" }
val presenterAndroid by extra { "io.github.privatik:presenter-android:$privatikLibraryVersion" }

