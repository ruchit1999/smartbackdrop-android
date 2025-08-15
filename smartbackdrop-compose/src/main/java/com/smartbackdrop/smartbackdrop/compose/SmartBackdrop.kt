package com.smartbackdrop.smartbackdrop.compose

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import com.smartbackdrop.smartbackdrop.core.Backdrop
import com.smartbackdrop.smartbackdrop.core.BackdropConfig
import com.smartbackdrop.smartbackdrop.core.BackdropSpec
import com.smartbackdrop.smartbackdrop.core.BlurBackdrop
import com.smartbackdrop.smartbackdrop.core.DefaultBackdropEngine
import com.smartbackdrop.smartbackdrop.core.GradientBackdrop
import com.smartbackdrop.smartbackdrop.core.MirrorBackdrop
import com.smartbackdrop.smartbackdrop.core.SolidBackdrop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Composable that creates a backdrop from an image and applies it as a background.
 */
@Composable
fun SmartBackdrop(
    imageModel: Any,
    modifier: Modifier = Modifier,
    config: BackdropConfig = BackdropConfig(),
    content: @Composable (BackdropSpec) -> Unit = {}
) {
    val context = LocalContext.current
    var spec by remember { mutableStateOf<BackdropSpec?>(null) }

    LaunchedEffect(imageModel, config) {
        spec = createBackdropSpec(context, imageModel, config)
    }

    spec?.let {
        val backgroundModifier = it.background.asModifier()
        Box(modifier.then(backgroundModifier)) {
            content(it)
        }
    } ?: run {
        ShimmerEffect(modifier)
    }
}

@Composable
fun ShimmerEffect(
    modifier: Modifier,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000
) {
    val shimmerColors = listOf(
        Color.White.copy(alpha = 0.3f),
        Color.White.copy(alpha = 0.5f),
        Color.LightGray.copy(alpha = 1f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 0.3f)
    )

    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Shimmer loading animation"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY)
    )

    Box(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(brush)
        )
    }
}

/**
 * Creates a backdrop specification from an image model.
 */
private suspend fun createBackdropSpec(
    context: android.content.Context,
    imageModel: Any,
    config: BackdropConfig
): BackdropSpec {
    val engine = DefaultBackdropEngine()
    val bitmap = loadBitmapFromModel(context, imageModel)
    return engine.buildSpec(bitmap, config)
}

/**
 * Loads a bitmap from various image model types.
 */
private suspend fun loadBitmapFromModel(
    context: android.content.Context,
    imageModel: Any
): Bitmap {
    return withContext(Dispatchers.IO) {
        val request = ImageRequest.Builder(context)
            .data(imageModel)
            .allowHardware(false)
            .build()

        val drawable = coil.ImageLoader(context).execute(request).drawable

        when (drawable) {
            is android.graphics.drawable.BitmapDrawable -> drawable.bitmap
            else -> createBitmapFromDrawable(drawable)
        }
    }
}

/**
 * Creates a bitmap from a drawable.
 */
private fun createBitmapFromDrawable(drawable: android.graphics.drawable.Drawable?): Bitmap {
    val width = drawable?.intrinsicWidth ?: 64
    val height = drawable?.intrinsicHeight ?: 64
    val bitmap = createBitmap(
        width.coerceAtLeast(1),
        height.coerceAtLeast(1),
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, width, height)
    drawable?.draw(canvas)

    return bitmap
}

/**
 * Renders backdrop as a Compose Modifier.
 */
fun Backdrop.asModifier(): Modifier = when (this) {
    is GradientBackdrop -> Modifier.drawBehind { drawRect(asBrush()!!) }
    is SolidBackdrop -> Modifier.drawBehind { drawRect(Color(color)) }
    is BlurBackdrop, is MirrorBackdrop -> {
        // Simple fallback: draw a darkened scrim as background
        Modifier.drawBehind { drawRect(Color(0xFF222222)) }
    }
}

/**
 * Converts a Backdrop to a Compose Brush where applicable.
 */
fun Backdrop.asBrush(): Brush? = when (this) {
    is GradientBackdrop -> Brush.horizontalGradient(colors.map { Color(it) })
    is SolidBackdrop -> Brush.verticalGradient(listOf(Color(color), Color(color)))
    else -> null // blur/mirror rendered in Modifier
}
