plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
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

// Publishing configuration for JitPack
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "com.github.ruchit1999"
                artifactId = "smartbackdrop-core"
                version = libs.versions.version.name.get()
                
                pom {
                    name.set("SmartBackdrop Core")
                    description.set("Core algorithms and engine for SmartBackdrop Android SDK")
                    url.set("https://github.com/ruchit1999/smartbackdrop-android")
                    
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("ruchit1999")
                            name.set("Ruchit Bhagat")
                            email.set("ruchitbhagat11@gmail.com")
                        }
                    }
                    
                    scm {
                        connection.set("scm:git:git://github.com/ruchit1999/smartbackdrop-android.git")
                        developerConnection.set("scm:git:ssh://github.com:ruchit1999/smartbackdrop-android.git")
                        url.set("https://github.com/ruchit1999/smartbackdrop-android")
                    }
                }
            }
        }
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.annotation)
    
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.espresso)
}


