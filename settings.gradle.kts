pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "smartbackdrop-android"
include(":smartbackdrop-core", ":smartbackdrop-compose", ":smartbackdrop-views", ":sample-app")
