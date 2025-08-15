package com.smartbackdrop.smartbackdrop.core

/**
 * Factory for creating backdrop strategies.
 */
object BackdropStrategyFactory {
    
    fun createStrategy(strategy: Strategy): BackdropStrategy {
        return when (strategy) {
            Strategy.EdgeGradientThenBlur -> EdgeGradientThenBlurStrategy()
            Strategy.BlurOnly -> BlurOnlyStrategy()
            Strategy.MirrorThenBlur -> MirrorThenBlurStrategy()
            Strategy.PaletteSolid -> PaletteSolidStrategy()
        }
    }
}

/**
 * Strategy interface for creating backdrops.
 */
interface BackdropStrategy {
    fun createBackdrop(analysis: ImageAnalysis): Backdrop
}

/**
 * Strategy for creating edge gradient with blur fallback.
 */
class EdgeGradientThenBlurStrategy : BackdropStrategy {
    override fun createBackdrop(analysis: ImageAnalysis): Backdrop {
        val gradient = GradientBackdrop(
            colors = listOf(analysis.edges.leftAvg, analysis.edges.rightAvg),
            angle = 0f
        )
        
        // Use blur if gutter ratio is high (very tall/wide aspect ratio)
        return if (analysis.gutterRatio > 0.25) {
            BlurBackdrop(radiusDp = 24f)
        } else {
            gradient
        }
    }
}

/**
 * Strategy for creating blur-only backdrop.
 */
class BlurOnlyStrategy : BackdropStrategy {
    override fun createBackdrop(analysis: ImageAnalysis): Backdrop {
        return BlurBackdrop(radiusDp = 24f)
    }
}

/**
 * Strategy for creating mirror with blur backdrop.
 */
class MirrorThenBlurStrategy : BackdropStrategy {
    override fun createBackdrop(analysis: ImageAnalysis): Backdrop {
        return MirrorBackdrop(blurRadiusDp = 8f)
    }
}

/**
 * Strategy for creating solid color backdrop from palette.
 */
class PaletteSolidStrategy : BackdropStrategy {
    override fun createBackdrop(analysis: ImageAnalysis): Backdrop {
        val darkNeutral = analysis.palette.minByOrNull { 
            ColorUtils.luminance(it) + ColorUtils.saturation(it) * 0.5 
        } ?: android.graphics.Color.DKGRAY
        
        return SolidBackdrop(color = darkNeutral)
    }
} 