package xyz.sleekstats.wheelapp
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import xyz.sleekstats.wheelapp.util.WheelUtils

/**
 * Unit tests for [getFinalAngle(), getSectionLength(), getWheelSectionIndex(), and determineDegreesToSpin()].
 */
class WheelUtilsTest {

    private val wheelUtils = WheelUtils()

    @Test
    fun getFinalAngle_ReturnsCorrectDegree() {
        var testNum = 44
        assertThat(wheelUtils.getFinalAngle(landingSpotInDegrees = testNum), `is`(44))

        testNum = (360 * 33) + 17
        assertThat(wheelUtils.getFinalAngle(landingSpotInDegrees = testNum), `is`(17))

        testNum  = (360 * 10) + 359
        assertThat(wheelUtils.getFinalAngle(landingSpotInDegrees = testNum), `is`(359))
    }

    @Test
    fun getSectionLength_ReturnsCorrectLength() {
        assertThat(wheelUtils.getSectionLength(sections = 5), `is`(360/5))
        assertThat(wheelUtils.getSectionLength(sections = 10), `is`(360/10))
        assertThat(wheelUtils.getSectionLength(sections = 3), `is`(360/3))
    }

    @Test
    fun getWheelSectionIndex_ReturnsCorrectIndex() {
        assertThat(wheelUtils.getWheelSectionIndex(finalAngle = 32, sectionLength = 40), `is`(0))
        assertThat(wheelUtils.getWheelSectionIndex(finalAngle = 359, sectionLength = 120), `is`(2))
    }

    @Test
    fun determineDegreesToSpin_IsInRange() {
        val randomDegrees = wheelUtils.determineDegreesToSpin()
        assertTrue(randomDegrees <= WheelUtils.LARGEST_SPIN)
        assertTrue(randomDegrees >= WheelUtils.SMALLEST_SPIN)
    }
}