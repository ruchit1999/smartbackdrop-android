plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
    signing
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

dependencies {
    implementation(project(":smartbackdrop-core"))
    
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    
    implementation(libs.coil)
    implementation(libs.coil.compose)
}

// Publishing configuration
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "com.github.smartbackdrop"
                artifactId = "smartbackdrop-compose"
                version = "1.0.0"
                
                pom {
                    name.set("SmartBackdrop Compose")
                    description.set("Jetpack Compose integration for SmartBackdrop Android SDK")
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
