package com.smartbackdrop.smartbackdrop.core

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Default implementation of backdrop engine.
 */
class DefaultBackdropEngine {
    
    private val imageAnalysisService = DefaultImageAnalysisService()
    private val foregroundColorService = DefaultForegroundColorService()

    suspend fun buildSpec(bitmap: Bitmap, config: BackdropConfig = BackdropConfig()): BackdropSpec {
        return withContext(Dispatchers.Default) {
            // Analyze image
            val analysis = imageAnalysisService.analyzeImage(bitmap, config)
            
            // Create backdrop using strategy pattern
            val strategy = BackdropStrategyFactory.createStrategy(config.strategy)
            val backdrop = strategy.createBackdrop(analysis)
            
            // Choose foreground colors
            val foreground = foregroundColorService.chooseForegroundColors(analysis.palette, backdrop)
            
            // Ensure contrast if required
            val finalForeground = if (config.enforceContrast) {
                foregroundColorService.ensureContrast(foreground, backdrop, config.targetContrast)
            } else {
                foreground
            }
            
            BackdropSpec(
                background = backdrop,
                foregroundColors = finalForeground
            )
        }
    }
}
