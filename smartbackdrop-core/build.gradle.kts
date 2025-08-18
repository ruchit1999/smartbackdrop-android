plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
    signing
}

android {
    namespace = "com.smartbackdrop.smartbackdrop.core"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables { useSupportLibrary = true }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { buildConfig = false }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.annotation:annotation:1.7.1")
    
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.espresso)
}

// Publishing configuration
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "com.github.smartbackdrop"
                artifactId = "smartbackdrop-core"
                version = libs.versions.version.name.get()
                
                pom {
                    name.set("SmartBackdrop Core")
                    description.set("Core algorithms and engine for SmartBackdrop Android SDK")
                    url.set("https://github.com/smartbackdrop/smartbackdrop-android")
                    
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("smartbackdrop")
                            name.set("SmartBackdrop Team")
                            email.set("support@smartbackdrop.dev")
                        }
                    }
                    
                    scm {
                        connection.set("scm:git:git://github.com/smartbackdrop/smartbackdrop-android.git")
                        developerConnection.set("scm:git:ssh://github.com:smartbackdrop/smartbackdrop-android.git")
                        url.set("https://github.com/smartbackdrop/smartbackdrop-android")
                    }
                }
            }
        }
    }
}
