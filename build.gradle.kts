buildscript {
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.6.21")
    }
}


plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}

apply(from = rootProject.file("dependencies.gradle.kts"))

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}