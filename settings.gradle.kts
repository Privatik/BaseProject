pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "BaseProject"
include(":app")
include(":core")
include(":core:ui")
include(":core:data")
include(":routing")
include(":auth:ui")
include(":auth:domain")
include(":auth:data")
include(":core:domain")
