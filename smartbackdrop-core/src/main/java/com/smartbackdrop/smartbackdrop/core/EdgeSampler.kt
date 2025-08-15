package com.smartbackdrop.smartbackdrop.core

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.roundToInt

object EdgeSampler {

    data class EdgeAverages(
        val leftAvg: Int,
        val rightAvg: Int,
        val leftVar: Double,
        val rightVar: Double
    )

    /**
     * Average left/right vertical strips using a trimmed mean if variance is high.
     * edgeStripPct defines the portion of width for each side.
     */
    fun sampleEdges(src: Bitmap, edgeStripPct: Float): EdgeAverages {
        val w = src.width
        val h = src.height
        val strip = (w * edgeStripPct).coerceAtLeast(1f).roundToInt().coerceAtMost(w/2)

        fun averageStrip(xStart: Int, xEnd: Int): Pair<Int, Double> {
            val colors = ArrayList<Int>(h * (xEnd - xStart))
            for (x in xStart until xEnd) {
                for (y in 0 until h) {
                    colors.add(src.getPixel(x, y))
                }
            }
            
            val lum = colors.map { ColorUtils.luminance(it) }.sorted()
            val n = lum.size
            val trim = (n * 0.10).roundToInt() // 10% top/bottom
            val trimmed = if (n > 20) lum.subList(trim, n - trim) else lum
            val meanLum = trimmed.average()
            
            // Find color with closest luminance in the trimmed set for stability
            val avgColor = colors.minByOrNull { kotlin.math.abs(ColorUtils.luminance(it) - meanLum) } ?: Color.BLACK
            val variance = trimmed.map { (it - meanLum)*(it - meanLum) }.average()
            return avgColor to variance
        }

        val (leftAvg, leftVar) = averageStrip(0, strip)
        val (rightAvg, rightVar) = averageStrip(w - strip, w)
        return EdgeAverages(leftAvg, rightAvg, leftVar, rightVar)
    }
}
