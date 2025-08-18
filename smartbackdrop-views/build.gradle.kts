plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.smartbackdrop.smartbackdrop.views"
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

// Publishing configuration
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "com.github.ruchit1999"
                artifactId = "smartbackdrop-views"
                version = libs.versions.version.name.get()
                
                pom {
                    name.set("SmartBackdrop Views")
                    description.set("Traditional Android Views integration for SmartBackdrop Android SDK")
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
                            email.set("ruchitbhagat11@gmail.com")
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
        
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/ruchit1999/smartbackdrop-android")
                credentials {
                    username = System.getenv("GITHUB_USERNAME") ?: project.findProperty("gpr.user") as String?
                    password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.key") as String?
                }
            }
            maven { url = uri("https://jitpack.io") }
        }
    }
}

dependencies {
    implementation(project(":smartbackdrop-core"))
    implementation(libs.coil)
    
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.espresso)

    // For Compose apps
    implementation("com.github.ruchit1999:smartbackdrop-compose:0.1.0")
    
    // For View-based apps
    implementation("com.github.ruchit1999:smartbackdrop-views:0.1.0")
    
    // Core module (automatically included)
    // implementation("com.github.ruchit1999:smartbackdrop-core:0.1.0")
}


