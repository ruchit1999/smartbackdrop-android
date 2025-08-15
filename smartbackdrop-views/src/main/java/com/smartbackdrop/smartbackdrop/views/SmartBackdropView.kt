package com.smartbackdrop.smartbackdrop.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout
import coil.ImageLoader
import coil.request.ImageRequest
import com.smartbackdrop.smartbackdrop.core.*
import kotlinx.coroutines.*

/**
 * Android View that creates and displays a backdrop from an image.
 */
class SmartBackdropView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val engine = DefaultBackdropEngine()

    /**
     * Binds an image model to this view and creates a backdrop.
     */
    fun bind(
        imageModel: Any,
        config: BackdropConfig = BackdropConfig(),
        onReady: (BackdropSpec) -> Unit = {}
    ) {
        scope.launch {
            val bitmap = withContext(Dispatchers.IO) { loadBitmap(imageModel) }
            val spec = withContext(Dispatchers.Default) { engine.buildSpec(bitmap, config) }
            
            applyBackdrop(spec.background)
            onReady(spec)
        }
    }

    /**
     * Loads a bitmap from various image model types.
     */
    private suspend fun loadBitmap(model: Any): Bitmap {
        val request = ImageRequest.Builder(context)
            .data(model)
            .allowHardware(false)
            .build()
        
        val result = ImageLoader(context).execute(request)
        val drawable = result.drawable
        
        return createBitmapFromDrawable(drawable)
    }

    /**
     * Creates a bitmap from a drawable.
     */
    private fun createBitmapFromDrawable(drawable: android.graphics.drawable.Drawable?): Bitmap {
        val width = drawable?.intrinsicWidth ?: 64
        val height = drawable?.intrinsicHeight ?: 64
        val bitmap = createBitmap(width.coerceAtLeast(1), height.coerceAtLeast(1), Bitmap.Config.ARGB_8888)
        
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, width, height)
        drawable?.draw(canvas)
        
        return bitmap
    }

    /**
     * Applies a backdrop to this view.
     */
    private fun applyBackdrop(backdrop: Backdrop) {
        val backgroundColor = when (backdrop) {
            is SolidBackdrop -> backdrop.color
            is GradientBackdrop -> backdrop.colors.first()
            is BlurBackdrop, is MirrorBackdrop -> 0xFF222222.toInt()
        }
        setBackgroundColor(backgroundColor)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scope.cancel()
    }
}
