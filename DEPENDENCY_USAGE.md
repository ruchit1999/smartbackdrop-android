# SmartBackdrop Dependency Usage Guide

This guide explains how to use SmartBackdrop Android SDK as a GitHub dependency in your projects.

## 🚀 Quick Setup

### 1. Add JitPack Repository

Add JitPack to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. Add Dependencies

Add to your `build.gradle.kts`:

```kotlin
dependencies {
    // For Compose apps
    implementation("com.github.smartbackdrop:smartbackdrop-compose:0.1.0")
    
    // For View-based apps
    implementation("com.github.smartbackdrop:smartbackdrop-views:0.1.0")
    
    // Core module (included automatically as transitive dependency)
    // implementation("com.github.smartbackdrop:smartbackdrop-core:0.1.0")
}
```

## 📦 Available Modules

### Core Module
```kotlin
implementation("com.github.smartbackdrop:smartbackdrop-core:0.1.0")
```
- Core algorithms and engine
- Image analysis and backdrop generation
- Color utilities and palette extraction
- Strategy patterns for backdrop creation

### Compose Module
```kotlin
implementation("com.github.smartbackdrop:smartbackdrop-compose:0.1.0")
```
- Jetpack Compose integration
- `SmartBackdrop` composable
- Compose-specific utilities and extensions
- Includes core module automatically

### Views Module
```kotlin
implementation("com.github.smartbackdrop:smartbackdrop-views:0.1.0")
```
- Traditional Android Views integration
- `SmartBackdropView` custom view
- View-specific utilities
- Includes core module automatically

## 🏷️ Version Options

### 1. Release Versions (Recommended)
Use specific release versions for stability:

```kotlin
dependencies {
    implementation("com.github.smartbackdrop:smartbackdrop-compose:v0.1.0")
    implementation("com.github.smartbackdrop:smartbackdrop-views:v0.1.0")
}
```

### 2. Latest Version
Use the latest version from the main branch:

```kotlin
dependencies {
    implementation("com.github.smartbackdrop:smartbackdrop-compose:main-SNAPSHOT")
    implementation("com.github.smartbackdrop:smartbackdrop-views:main-SNAPSHOT")
}
```

### 3. Specific Commit
Use a specific commit hash for exact version control:

```kotlin
dependencies {
    implementation("com.github.smartbackdrop:smartbackdrop-compose:abc1234")
    implementation("com.github.smartbackdrop:smartbackdrop-views:abc1234")
}
```

### 4. Branch
Use a specific branch:

```kotlin
dependencies {
    implementation("com.github.smartbackdrop:smartbackdrop-compose:develop")
    implementation("com.github.smartbackdrop:smartbackdrop-views:develop")
}
```

## 🔧 Configuration Options

### Gradle Configuration

#### Kotlin DSL (`build.gradle.kts`)
```kotlin
android {
    compileSdk = 34
    
    defaultConfig {
        minSdk = 21
        targetSdk = 34
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("com.github.smartbackdrop:smartbackdrop-compose:0.1.0")
}
```

#### Groovy DSL (`build.gradle`)
```groovy
android {
    compileSdk 34
    
    defaultConfig {
        minSdk 21
        targetSdk 34
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation 'com.github.smartbackdrop:smartbackdrop-compose:0.1.0'
}
```

## 📱 Usage Examples

### Compose Usage
```kotlin
import com.smartbackdrop.smartbackdrop.compose.SmartBackdrop

@Composable
fun MyScreen() {
    SmartBackdrop(
        imageModel = "https://example.com/image.jpg",
        modifier = Modifier.fillMaxWidth().height(220.dp)
    ) { backdropSpec ->
        Text(
            "Beautiful Image",
            color = Color(backdropSpec.foregroundColors.onBackground),
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        )
    }
}
```

### Views Usage
```kotlin
import com.smartbackdrop.smartbackdrop.views.SmartBackdropView

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val backdropView = SmartBackdropView(this)
        backdropView.bind("https://example.com/image.jpg") { spec ->
            // Backdrop is ready!
        }
        
        setContentView(backdropView)
    }
}
```

## 🔍 Troubleshooting

### Common Issues

#### Issue: Dependency not found
```kotlin
// Make sure JitPack repository is added
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

#### Issue: Version not available
```kotlin
// Check available versions at: https://jitpack.io/#smartbackdrop/smartbackdrop-android
// Use a valid version or commit hash
implementation("com.github.smartbackdrop:smartbackdrop-compose:v0.1.0")
```

#### Issue: Build errors
```kotlin
// Ensure you have the correct Android configuration
android {
    compileSdk = 34
    minSdk = 21
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
```

#### Issue: Compose version conflicts
```kotlin
// Use the BOM to manage Compose versions
dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("com.github.smartbackdrop:smartbackdrop-compose:0.1.0")
}
```

## 📊 Version Compatibility

| SmartBackdrop Version | Android API | Kotlin Version | Compose Version |
|----------------------|-------------|----------------|-----------------|
| 0.1.0                | 21+         | 1.9.0+         | 1.5.0+          |

## 🔄 Updating Dependencies

### Automatic Updates
Use Dependabot for automatic updates:

1. Go to your repository settings
2. Enable Dependabot alerts
3. Add `.github/dependabot.yml`:

```yaml
version: 2
updates:
  - package-ecosystem: "gradle"
    directory: "/"
    schedule:
      interval: "weekly"
```

### Manual Updates
Check for new versions at:
- [JitPack SmartBackdrop](https://jitpack.io/#smartbackdrop/smartbackdrop-android)
- [GitHub Releases](https://github.com/smartbackdrop/smartbackdrop-android/releases)

## 🎯 Best Practices

### 1. Use Release Versions
```kotlin
// ✅ Good - Use release versions
implementation("com.github.smartbackdrop:smartbackdrop-compose:v0.1.0")

// ❌ Avoid - Using SNAPSHOT in production
implementation("com.github.smartbackdrop:smartbackdrop-compose:main-SNAPSHOT")
```

### 2. Specify Versions Explicitly
```kotlin
// ✅ Good - Explicit version
implementation("com.github.smartbackdrop:smartbackdrop-compose:0.1.0")

// ❌ Avoid - Dynamic version
implementation("com.github.smartbackdrop:smartbackdrop-compose:+")
```

### 3. Use Appropriate Modules
```kotlin
// ✅ Good - Use only what you need
implementation("com.github.smartbackdrop:smartbackdrop-compose:0.1.0")

// ❌ Avoid - Including unnecessary modules
implementation("com.github.smartbackdrop:smartbackdrop-core:0.1.0")
implementation("com.github.smartbackdrop:smartbackdrop-compose:0.1.0")
```

## 📞 Support

For dependency-related issues:

- **JitPack Issues**: [JitPack Support](https://jitpack.io/docs/ANDROID.html)
- **GitHub Issues**: [SmartBackdrop Issues](https://github.com/smartbackdrop/smartbackdrop-android/issues)
- **Documentation**: [SmartBackdrop Docs](https://github.com/smartbackdrop/smartbackdrop-android#readme)

## 🔗 Useful Links

- [JitPack SmartBackdrop Page](https://jitpack.io/#smartbackdrop/smartbackdrop-android)
- [GitHub Repository](https://github.com/smartbackdrop/smartbackdrop-android)
- [Release Notes](https://github.com/smartbackdrop/smartbackdrop-android/releases)
- [API Documentation](https://github.com/smartbackdrop/smartbackdrop-android#readme)

---

**Happy coding with SmartBackdrop!** 🎉 