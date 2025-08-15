package com.smartbackdrop.smartbackdrop.core

import android.graphics.Color

/**
 * Service responsible for choosing foreground colors and ensuring contrast compliance.
 */
class DefaultForegroundColorService {
    
    fun chooseForegroundColors(palette: List<Int>, backdrop: Backdrop): Foreground {
        val primary = palette.firstOrNull() ?: Color.WHITE
        val bgColor = getBackgroundColor(backdrop, palette)
        val onBackground = chooseTextColor(bgColor)
        val onPrimary = chooseTextColor(primary)
        
        return Foreground(primary, onPrimary, onBackground)
    }
    
    fun ensureContrast(foreground: Foreground, backdrop: Backdrop, targetContrast: Double): Foreground {
        val bgColor = getBackgroundColor(backdrop, listOf())
        var onBg = foreground.onBackground
        var scrimPct = 0.0
        
        while (ColorUtils.contrastRatio(onBg, bgColor) < targetContrast && scrimPct < 0.5) {
            scrimPct += 0.05
            val mixed = ColorUtils.mixLinear(bgColor, Color.BLACK, scrimPct)
            if (ColorUtils.contrastRatio(onBg, mixed) >= targetContrast) {
                return foreground.copy(onBackground = onBg)
            }
        }
        
        // If still not enough, flip color
        onBg = if (onBg == Color.BLACK) Color.WHITE else Color.BLACK
        return foreground.copy(onBackground = onBg)
    }
    
    private fun getBackgroundColor(backdrop: Backdrop, palette: List<Int>): Int {
        return when (backdrop) {
            is SolidBackdrop -> backdrop.color
            is GradientBackdrop -> ColorUtils.mixLinear(backdrop.colors.first(), backdrop.colors.last(), 0.5)
            is BlurBackdrop, is MirrorBackdrop -> chooseDarkNeutral(palette)
        }
    }
    
    private fun chooseDarkNeutral(palette: List<Int>): Int {
        return palette.minByOrNull { 
            ColorUtils.luminance(it) + ColorUtils.saturation(it) * 0.5 
        } ?: Color.DKGRAY
    }
    
    private fun chooseTextColor(backgroundColor: Int): Int {
        return if (ColorUtils.contrastRatio(Color.BLACK, backgroundColor) >= 4.5) {
            Color.BLACK
        } else {
            Color.WHITE
        }
    }
} 