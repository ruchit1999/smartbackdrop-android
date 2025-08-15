package com.smartbackdrop.smartbackdrop.core

import android.graphics.Bitmap

// Core backdrop types
sealed interface Backdrop

data class GradientBackdrop(val colors: List<Int>, val angle: Float = 0f) : Backdrop
data class BlurBackdrop(val radiusDp: Float = 20f) : Backdrop
data class MirrorBackdrop(val blurRadiusDp: Float = 6f) : Backdrop
data class SolidBackdrop(val color: Int) : Backdrop

// Image analysis result
data class ImageAnalysis(
    val edges: EdgeAnalysis,
    val palette: List<Int>,
    val gutterRatio: Double
)

data class EdgeAnalysis(
    val leftAvg: Int,
    val rightAvg: Int
)

// Foreground color data
data class Foreground(
    val primary: Int, 
    val onPrimary: Int, 
    val onBackground: Int
)

// Strategy enumeration
enum class Strategy {
    PaletteSolid,
    EdgeGradientThenBlur,
    BlurOnly,
    MirrorThenBlur
}

// Configuration
data class BackdropConfig(
    val strategy: Strategy = Strategy.PaletteSolid,
    val enforceContrast: Boolean = true,
    val targetContrast: Double = 4.5,
    val downscaleMax: Int = 256,
    val edgeStripPct: Float = 0.08f
)

// Result specification
data class BackdropSpec(
    val background: Backdrop,
    val foregroundColors: Foreground
)
