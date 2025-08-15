package com.smartbackdrop.smartbackdrop.core

import android.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ColorUtilsTest {
    @Test fun contrast_basic() {
        val ratio = ColorUtils.contrastRatio(Color.BLACK, Color.WHITE)
        assertEquals(true, ratio > 20.0)
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class EdgeSamplerTest {
    @Test fun edge_avg_on_gradient() {
        val w = 20; val h = 10
        val bmp = android.graphics.Bitmap.createBitmap(w, h, android.graphics.Bitmap.Config.ARGB_8888)
        // left half black, right half white
        for (x in 0 until w) {
            val c = if (x < w/2) Color.BLACK else Color.WHITE
            for (y in 0 until h) bmp.setPixel(x, y, c)
        }
        val res = EdgeSampler.sampleEdges(bmp, 0.1f)
        assertEquals(Color.BLACK, res.leftAvg)
        assertEquals(Color.WHITE, res.rightAvg)
    }
}
