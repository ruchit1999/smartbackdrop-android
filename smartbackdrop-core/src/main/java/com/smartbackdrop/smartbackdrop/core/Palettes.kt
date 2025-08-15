package com.smartbackdrop.smartbackdrop.core

import android.graphics.Color
import kotlin.math.sqrt
import kotlin.random.Random

object KMeansPalette {
    data class Swatch(val color: Int, val population: Int)

    fun extract(colors: IntArray, n: Int = 5, seed: Long = 42L): List<Swatch> {
        if (colors.isEmpty()) return emptyList()
        
        val rnd = Random(seed)
        val points = colors.map { ColorUtils.argbToLab(it) }
        val k = n.coerceAtMost(points.size)
        val centers = points.shuffled(rnd).take(k).toMutableList()
        val assignments = IntArray(points.size)
        
        repeat(10) {
            // assign points to nearest center
            for (i in points.indices) {
                var best = 0
                var bestd = Double.MAX_VALUE
                for (c in 0 until k) {
                    val d = dist(points[i], centers[c])
                    if (d < bestd) { bestd = d; best = c }
                }
                assignments[i] = best
            }
            
            // update centers
            val sums = Array(k) { DoubleArray(3) }
            val counts = IntArray(k)
            for (i in points.indices) {
                val a = assignments[i]
                sums[a][0] += points[i].L
                sums[a][1] += points[i].a
                sums[a][2] += points[i].b
                counts[a]++
            }
            for (c in 0 until k) {
                if (counts[c] > 0) {
                    centers[c] = ColorUtils.Lab(sums[c][0]/counts[c], sums[c][1]/counts[c], sums[c][2]/counts[c])
                }
            }
        }
        
        // build swatches from centers + counts
        val counts = IntArray(k)
        for (a in assignments) counts[a]++
        return (0 until k).map { idx ->
            val color = ColorUtils.labToArgb(centers[idx])
            Swatch(color, counts[idx])
        }.sortedByDescending { it.population }
    }

    private fun dist(p: ColorUtils.Lab, c: ColorUtils.Lab): Double {
        val dl = p.L - c.L
        val da = p.a - c.a
        val db = p.b - c.b
        return sqrt(dl*dl + da*da + db*db)
    }
}

object MedianCutPalette {
    data class Swatch(val color: Int, val population: Int)

    private fun channel(c: Int, which: Int): Int = when (which) {
        0 -> (c shr 16) and 0xFF
        1 -> (c shr 8) and 0xFF
        else -> (c) and 0xFF
    }

    fun extract(colors: IntArray, n: Int = 5): List<Swatch> {
        if (colors.isEmpty()) return emptyList()
        
        var boxes = listOf(colors)
        while (boxes.size < n) {
            val newBoxes = mutableListOf<IntArray>()
            for (boxColors in boxes) {
                val rmin = boxColors.minOf { channel(it,0) }
                val rmax = boxColors.maxOf { channel(it,0) }
                val gmin = boxColors.minOf { channel(it,1) }
                val gmax = boxColors.maxOf { channel(it,1) }
                val bmin = boxColors.minOf { channel(it,2) }
                val bmax = boxColors.maxOf { channel(it,2) }
                val rRange = rmax - rmin
                val gRange = gmax - gmin
                val bRange = bmax - bmin
                val axis = if (rRange >= gRange && rRange >= bRange) 0 else if (gRange >= bRange) 1 else 2
                val sorted = boxColors.sortedBy { channel(it, axis) }
                val mid = sorted.size / 2
                newBoxes += sorted.subList(0, mid).toIntArray()
                newBoxes += sorted.subList(mid, sorted.size).toIntArray()
            }
            boxes = newBoxes
            if (boxes.any { it.isEmpty() }) break
        }
        
        return boxes.filter { it.isNotEmpty() }.map { boxColors ->
            val avg = boxColors.fold(Triple(0L,0L,0L)) { acc, c ->
                Triple(acc.first + ((c shr 16) and 0xFF), acc.second + ((c shr 8) and 0xFF), acc.third + (c and 0xFF))
            }
            val npx = boxColors.size
            val r = (avg.first / npx).toInt()
            val g = (avg.second / npx).toInt()
            val bl = (avg.third / npx).toInt()
            val color = Color.rgb(r,g,bl)
            Swatch(color, npx)
        }.sortedByDescending { it.population }
    }
}
