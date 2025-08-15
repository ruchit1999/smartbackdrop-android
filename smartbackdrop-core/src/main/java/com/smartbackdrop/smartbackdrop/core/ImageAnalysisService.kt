package com.smartbackdrop.smartbackdrop.core

import android.graphics.*
import android.graphics.Bitmap.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.*

/**
 * Service responsible for analyzing images to extract edges, palette, and other features.
 */
class DefaultImageAnalysisService {
    
    suspend fun analyzeImage(bitmap: Bitmap, config: BackdropConfig): ImageAnalysis {
        return withContext(Dispatchers.Default) {
            val preparedBitmap = prepareForAnalysis(bitmap, config.downscaleMax)
            val edges = EdgeSampler.sampleEdges(preparedBitmap, config.edgeStripPct)
            val palette = extractPalette(preparedBitmap)
            val gutterRatio = computeGutterRatio(bitmap)
            
            ImageAnalysis(
                edges = EdgeAnalysis(edges.leftAvg, edges.rightAvg),
                palette = palette,
                gutterRatio = gutterRatio
            )
        }
    }
    
    private fun prepareForAnalysis(src: Bitmap, downscaleMax: Int): Bitmap {
        val maxDim = max(src.width, src.height)
        if (maxDim <= downscaleMax && src.config == Bitmap.Config.ARGB_8888) return src
        
        val ratio = downscaleMax.toFloat() / maxDim.toFloat()
        val w = (src.width * ratio).roundToInt().coerceAtLeast(1)
        val h = (src.height * ratio).roundToInt().coerceAtLeast(1)
        val dst = createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(dst)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        c.drawBitmap(src, null, Rect(0, 0, w, h), p)
        return dst
    }
    
    private fun extractPalette(bmp: Bitmap): List<Int> {
        val colors = IntArray(bmp.width * bmp.height)
        bmp.getPixels(colors, 0, bmp.width, 0, 0, bmp.width, bmp.height)
        val unique = colors.filter { Color.alpha(it) > 0 }.toIntArray()
        val km = KMeansPalette.extract(unique, n = 5, seed = 42L)
        val list = km.ifEmpty { 
            MedianCutPalette.extract(unique, n = 5).map { KMeansPalette.Swatch(it.color, it.population) } 
        }
        return list.sortedByDescending { it.population }.map { it.color }
    }
    
    private fun computeGutterRatio(src: Bitmap): Double {
        val w = src.width.toDouble()
        val h = src.height.toDouble()
        val aspect = w / h
        return if (aspect > 1.0) {
            (1.0 - (1.0 / aspect)).coerceIn(0.0, 1.0)
        } else {
            (1.0 - aspect).coerceIn(0.0, 1.0)
        }
    }
} 