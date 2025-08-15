# Quick Start Guide

Get up and running with SmartBackdrop Android SDK in minutes!

## 🚀 Installation

### Step 1: Add Repository

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

### Step 2: Add Dependencies

Add to your `build.gradle.kts`:

```kotlin
dependencies {
    // For Compose apps
    implementation("com.github.smartbackdrop:smartbackdrop-android:compose:1.0.0")
    
    // For View-based apps
    implementation("com.github.smartbackdrop:smartbackdrop-android:views:1.0.0")
}
```

## 📱 Basic Usage

### Compose (Recommended)

```kotlin
import com.smartbackdrop.smartbackdrop.compose.SmartBackdrop

@Composable
fun MyScreen() {
    SmartBackdrop(
        imageModel = "https://example.com/image.jpg",
        modifier = Modifier.fillMaxWidth().height(220.dp)
    ) { backdropSpec ->
        // Your content here
        Text(
            "Beautiful Image",
            color = Color(backdropSpec.foregroundColors.onBackground),
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        )
    }
}
```

### Views

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

## 🎨 Customization

### Different Strategies

```kotlin
SmartBackdrop(
    imageModel = imageUrl,
    config = BackdropConfig(
        strategy = Strategy.EdgeGradientThenBlur, // Try different strategies
        enforceContrast = true,
        edgeStripPct = 0.15f
    )
) { spec ->
    // Your content
}
```

### Available Strategies

- `Strategy.PaletteSolid` - Creates solid color from image palette (default)
- `Strategy.EdgeGradientThenBlur` - Creates gradient from left/right edges
- `Strategy.BlurOnly` - Applies blur effect
- `Strategy.MirrorThenBlur` - Mirrors and blurs the image

## 📋 Configuration Options

```kotlin
val config = BackdropConfig(
    strategy = Strategy.PaletteSolid,
    enforceContrast = true,        // Ensures WCAG 4.5:1 contrast ratio
    targetContrast = 4.5,          // Target contrast ratio
    downscaleMax = 256,            // Max dimension for analysis (performance)
    edgeStripPct = 0.08f           // Percentage of image width for edge analysis
)
```

## 🔧 Advanced Usage

### Handling Different Backdrop Types

```kotlin
SmartBackdrop(imageModel = imageUrl) { spec ->
    when (spec.background) {
        is GradientBackdrop -> {
            // Handle gradient backdrop
            Text("Gradient Backdrop")
        }
        is SolidBackdrop -> {
            // Handle solid color backdrop
            Text("Solid Backdrop")
        }
        is BlurBackdrop -> {
            // Handle blur backdrop
            Text("Blur Backdrop")
        }
        is MirrorBackdrop -> {
            // Handle mirror backdrop
            Text("Mirror Backdrop")
        }
    }
    
    // Use foreground colors for text
    Text(
        "Title",
        color = Color(spec.foregroundColors.onBackground)
    )
}
```

### Custom Image Sources

SmartBackdrop supports various image sources:

```kotlin
// URL
SmartBackdrop(imageModel = "https://example.com/image.jpg")

// Resource ID
SmartBackdrop(imageModel = R.drawable.my_image)

// File
SmartBackdrop(imageModel = File("/path/to/image.jpg"))

// Uri
SmartBackdrop(imageModel = Uri.parse("content://..."))

// Bitmap
SmartBackdrop(imageModel = myBitmap)
```

## 🎯 Common Use Cases

### Video Player Backdrop

```kotlin
SmartBackdrop(
    imageModel = videoThumbnailUrl,
    modifier = Modifier.fillMaxSize()
) { spec ->
    VideoPlayer(
        modifier = Modifier.fillMaxSize(),
        controls = {
            Text(
                "Video Title",
                color = Color(spec.foregroundColors.onBackground),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}
```

### Image Gallery

```kotlin
LazyColumn {
    items(images) { image ->
        SmartBackdrop(
            imageModel = image.url,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) { spec ->
            AsyncImage(
                model = image.url,
                contentDescription = image.description,
                modifier = Modifier.fillMaxSize()
            )
            
            Text(
                image.title,
                color = Color(spec.foregroundColors.onBackground),
                modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
            )
        }
    }
}
```

### Profile Header

```kotlin
SmartBackdrop(
    imageModel = user.profileImageUrl,
    modifier = Modifier.fillMaxWidth().height(300.dp)
) { spec ->
    Column(
        modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
    ) {
        Text(
            user.name,
            color = Color(spec.foregroundColors.onBackground),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            user.bio,
            color = Color(spec.foregroundColors.onBackground).copy(alpha = 0.8f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
```

## 🐛 Troubleshooting

### Common Issues

**Issue**: Backdrop not showing
```kotlin
// Make sure you have proper permissions for network images
<uses-permission android:name="android.permission.INTERNET" />
```

**Issue**: Performance problems
```kotlin
// Reduce downscaleMax for better performance
val config = BackdropConfig(downscaleMax = 128)
```

**Issue**: Text not readable
```kotlin
// Enable contrast enforcement
val config = BackdropConfig(enforceContrast = true)
```

### Debug Mode

Enable debug logging:

```kotlin
SmartBackdrop(
    imageModel = imageUrl,
    config = BackdropConfig(
        // Add debug info
    )
) { spec ->
    // spec.debugInfo contains analysis details
}
```

## 📚 Next Steps

1. **Try the Sample App**: Run the included sample app to see all features
2. **Read the Full Documentation**: Check the main README for detailed API docs
3. **Join the Community**: Report issues or ask questions on GitHub
4. **Contribute**: Help improve SmartBackdrop by contributing code

## 🆘 Need Help?

- **GitHub Issues**: [Report bugs](https://github.com/smartbackdrop/smartbackdrop-android/issues)
- **GitHub Discussions**: [Ask questions](https://github.com/smartbackdrop/smartbackdrop-android/discussions)
- **Email**: support@smartbackdrop.dev

---

**Happy coding!** 🎉 