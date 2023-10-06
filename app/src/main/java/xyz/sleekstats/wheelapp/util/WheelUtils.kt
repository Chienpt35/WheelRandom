package xyz.sleekstats.wheelapp.util

import xyz.sleekstats.wheelapp.ui.wheel.WheelActivity
import kotlin.random.Random

class WheelUtils {

    fun getSectionLength(sections: Int): Int = WheelActivity.DEGREES_IN_CIRCLE.toInt() / sections

    fun getWheelSectionIndex(finalAngle: Int, sectionLength: Int): Int = finalAngle / sectionLength

    fun getFinalAngle(landingSpotInDegrees: Int): Int = landingSpotInDegrees % WheelActivity.DEGREES_IN_CIRCLE.toInt()

    fun determineDegreesToSpin(): Int = SMALLEST_SPIN + Random.nextInt(
        SPIN_VARIANCE
    )


    companion object {
        const val SMALLEST_SPIN = 500
        const val LARGEST_SPIN = 1500
        const val SPIN_VARIANCE = LARGEST_SPIN - SMALLEST_SPIN
    }
}