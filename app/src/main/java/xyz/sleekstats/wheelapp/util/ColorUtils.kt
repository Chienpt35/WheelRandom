package xyz.sleekstats.wheelapp.util

import android.graphics.Color

class ColorUtils {

    val wheelToTextColorsMap: Map<Int, Int> = mapOf(
        Color.RED to Color.WHITE,
        Color.BLUE to Color.WHITE,
        Color.GREEN to Color.BLACK,
        Color.YELLOW to Color.BLACK,
        Color.MAGENTA to Color.BLACK
    )

    val wheelToBackgroundColorsMap: Map<Int, Int> = mapOf(
        Color.RED to Color.parseColor("#FF8585"),
        Color.BLUE to Color.parseColor("#8585FF"),
        Color.GREEN to Color.parseColor("#85FF85"),
        Color.YELLOW to Color.parseColor("#FFFF85"),
        Color.MAGENTA to Color.parseColor("#FF85FF")
    )
}