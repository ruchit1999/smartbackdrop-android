
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
}

subprojects {
    afterEvaluate {
        // Common Java/Kotlin options can go here if needed
    }
}
