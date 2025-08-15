package com.smartbackdrop.smartbackdrop.core

import android.graphics.Color
import kotlin.math.*

/**
 * Color conversions and math utilities.
 * sRGB <-> Linear, LAB, luminance, WCAG contrast.
 */
object ColorUtils {

    data class Lab(val L: Double, val a: Double, val b: Double)

    // D65 reference white
    private const val Xn = 95.047
    private const val Yn = 100.0
    private const val Zn = 108.883

    private fun srgbToLinear(c: Double): Double =
        if (c <= 0.04045) c / 12.92 else ((c + 0.055) / 1.055).pow(2.4)

    private fun linearToSrgb(c: Double): Double =
        if (c <= 0.0031308) 12.92 * c else (1.055 * c.pow(1.0 / 2.4) - 0.055)

    fun argbToLinearRgbInt(argb: Int): Triple<Double, Double, Double> {
        val r = Color.red(argb) / 255.0
        val g = Color.green(argb) / 255.0
        val b = Color.blue(argb) / 255.0
        return Triple(srgbToLinear(r), srgbToLinear(g), srgbToLinear(b))
    }

    fun linearRgbToArgb(r: Double, g: Double, b: Double, a: Int = 255): Int {
        val sr = (linearToSrgb(r).coerceIn(0.0, 1.0) * 255.0 + 0.5).toInt()
        val sg = (linearToSrgb(g).coerceIn(0.0, 1.0) * 255.0 + 0.5).toInt()
        val sb = (linearToSrgb(b).coerceIn(0.0, 1.0) * 255.0 + 0.5).toInt()
        return Color.argb(a, sr, sg, sb)
    }

    fun argbToLab(argb: Int): Lab {
        val (rl, gl, bl) = argbToLinearRgbInt(argb)
        // linear RGB to XYZ
        val X = 100.0 * (0.4124 * rl + 0.3576 * gl + 0.1805 * bl)
        val Y = 100.0 * (0.2126 * rl + 0.7152 * gl + 0.0722 * bl)
        val Z = 100.0 * (0.0193 * rl + 0.1192 * gl + 0.9505 * bl)
        fun f(t: Double): Double {
            val d = 6.0/29.0
            return if (t > d*d*d) cbrt(t) else (t / (3*d*d) + 4.0/29.0)
        }
        val fx = f(X / Xn)
        val fy = f(Y / Yn)
        val fz = f(Z / Zn)
        val L = 116 * fy - 16
        val a = 500 * (fx - fy)
        val b = 200 * (fy - fz)
        return Lab(L, a, b)
    }

    fun labToArgb(lab: Lab): Int {
        val fy = (lab.L + 16) / 116.0
        val fx = lab.a / 500.0 + fy
        val fz = fy - lab.b / 200.0
        fun finv(t: Double): Double {
            val d = 6.0/29.0
            return if (t > d) t*t*t else 3*d*d*(t - 4.0/29.0)
        }
        val X = Xn * finv(fx)
        val Y = Yn * finv(fy)
        val Z = Zn * finv(fz)
        // XYZ to linear RGB
        val rl =  3.2406 * X/100.0 + -1.5372 * Y/100.0 + -0.4986 * Z/100.0
        val gl = -0.9689 * X/100.0 +  1.8758 * Y/100.0 +  0.0415 * Z/100.0
        val bl =  0.0557 * X/100.0 + -0.2040 * Y/100.0 +  1.0570 * Z/100.0
        return linearRgbToArgb(rl, gl, bl)
    }

    fun luminance(argb: Int): Double {
        val (r, g, b) = argbToLinearRgbInt(argb)
        return 0.2126 * r + 0.7152 * g + 0.0722 * b
    }

    fun contrastRatio(fg: Int, bg: Int): Double {
        val l1 = luminance(fg) + 0.05
        val l2 = luminance(bg) + 0.05
        return max(l1, l2) / min(l1, l2)
    }

    fun mixLinear(c1: Int, c2: Int, t: Double): Int {
        val (r1,g1,b1) = argbToLinearRgbInt(c1)
        val (r2,g2,b2) = argbToLinearRgbInt(c2)
        return linearRgbToArgb(
            r1*(1-t) + r2*t,
            g1*(1-t) + g2*t,
            b1*(1-t) + b2*t
        )
    }

    fun saturation(argb: Int): Double {
        val (r,g,b) = argbToLinearRgbInt(argb)
        val maxc = max(r, max(g,b))
        val minc = min(r, min(g,b))
        return if (maxc == 0.0) 0.0 else (maxc - minc) / maxc
    }
}
