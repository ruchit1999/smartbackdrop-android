plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.smartbackdrop.smartbackdrop.compose"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release { isMinifyEnabled = false }
        debug { isMinifyEnabled = false }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

// Publishing configuration for JitPack
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "com.github.ruchit1999"
                artifactId = "smartbackdrop-compose"
                version = libs.versions.version.name.get()
                
                pom {
                    name.set("SmartBackdrop Compose")
                    description.set("Jetpack Compose integration for SmartBackdrop Android SDK")
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
    implementation(project(":smartbackdrop-core"))
    
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    
    implementation(libs.coil)
    implementation(libs.coil.compose)
}


