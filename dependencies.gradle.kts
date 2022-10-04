//versions
val coreKtxVersion by extra { "1.7.0" }
val lifecycleRuntimeKtxVersion by extra { "2.3.1" }
val activityComposeVersion by extra { "1.3.1" }
val composeVersion by extra {"1.1.1" }

//dependencies
val compose by extra {
    listOf(
        "androidx.compose.ui:ui:${composeVersion}",
        "androidx.compose.material:material:${composeVersion}",
        "androidx.compose.ui:ui-tooling-preview:${composeVersion}",
        "androidx.compose.ui:ui-tooling:${composeVersion}",
        "androidx.compose.ui:ui-test-manifest:${composeVersion}"
    )
}

val coreKtx by extra { "androidx.core:core-ktx:${coreKtxVersion}" }
val lifecycleRuntimeKtx by extra { "androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleRuntimeKtxVersion}" }
val activityCompose by extra { "androidx.activity:activity-compose:${activityComposeVersion}" }
