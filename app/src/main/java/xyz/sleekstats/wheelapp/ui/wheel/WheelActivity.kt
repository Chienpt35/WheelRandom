package xyz.sleekstats.wheelapp.ui.wheel

import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wheel.*
import xyz.sleekstats.wheelapp.R
import xyz.sleekstats.wheelapp.db.WheelChoiceDatabase
import xyz.sleekstats.wheelapp.model.WheelChoice
import xyz.sleekstats.wheelapp.util.ColorUtils

/**This Activity displays a wheel of user-entered choices, along with a button that allows the user to spin the wheel.
 * The wheel will eventually stop on one of the possible choices based on the random amount of degrees it's spun.
 **/
class WheelActivity : AppCompatActivity(), WheelContract.View {

    private lateinit var wheelPresenter: WheelPresenter
    private val colorUtils = ColorUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val displayMode = resources.configuration.orientation

        if (displayMode == Configuration.ORIENTATION_LANDSCAPE) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.show()
        }
        setContentView(R.layout.activity_wheel)

        wheelPresenter = WheelPresenter(this)
        wheelPresenter.getWheelChoices()
    }

    override fun buildWheel(wheelChoices: List<WheelChoice>) {
        wheel_view.drawWheel(wheelChoices, getWidthOfScreen())

        spin_button.setOnClickListener {
            wheelPresenter.determineSpin()
        }
    }

    private fun getWidthOfScreen(): Int {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    //Rotate wheel the amount of degrees entered and update starting point
    override fun spinWheel(wheelStartingDegrees: Float, wheelEndingDegrees: Float) {
        val rotateAnimation = createRotateAnimation(wheelStartingDegrees, wheelEndingDegrees)
        wheel_view.startAnimation(rotateAnimation)
    }

    //Create animation that rotates object around its center a certain amount of degrees and keeps it there
    private fun createRotateAnimation(fromDegrees: Float, toDegrees: Float): RotateAnimation {
        val rotateAnimation = RotateAnimation(
            -fromDegrees, -toDegrees,
            Animation.RELATIVE_TO_SELF, .5f,
            Animation.RELATIVE_TO_SELF, .5f
        )
        rotateAnimation.duration = SPIN_DURATION
        rotateAnimation.fillAfter = true
        rotateAnimation.interpolator = DecelerateInterpolator()
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                winnerDisplayTextView.text = ""
                spin_button.isClickable = false
            }

            override fun onAnimationEnd(p0: Animation?) {
                wheelPresenter.determineChoiceWheelLandsOn(toDegrees.toInt())
                spin_button.isClickable = true
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
        return rotateAnimation
    }

    override fun displayWinner(choiceText: String, colorCode: Int) {
        winnerDisplayTextView.text = choiceText
        val color = colorUtils.wheelToBackgroundColorsMap[colorCode]
        if (color != null) { wheel_layout.setBackgroundColor(color) }
    }

    override fun provideDatabase(): WheelChoiceDatabase {
        return WheelChoiceDatabase.getDatabase(applicationContext)
    }

    override fun onNotEnoughWheelChoices() {
        Toast.makeText(this, getString(R.string.enter_more_choices_warning), Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        const val DEGREES_IN_CIRCLE: Float = 360.0f
        const val SPIN_DURATION: Long = 3000
    }
}
