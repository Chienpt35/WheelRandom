package xyz.sleekstats.wheelapp.ui.wheel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.sleekstats.wheelapp.model.WheelChoice
import xyz.sleekstats.wheelapp.util.WheelUtils

class WheelPresenter(private val wheelView: WheelContract.View) :
    WheelContract.Presenter {

    private var wheelStartingDegrees = 0f
    private var wheelEndingDegrees = 0f
    private var wheelChoices: List<WheelChoice> = arrayListOf()
    private val wheelUtils = WheelUtils()

    private val wheelChoiceDatabase = wheelView.provideDatabase()
    private val wheelChoiceDAO = wheelChoiceDatabase.wheelChoiceDao()

    //Retrieve all wheel choices from database, filtering out those without text.
    // If there's at least 2 tell view to build a wheel with choices.
    override fun getWheelChoices() {
        CoroutineScope(Dispatchers.Main).launch {
            wheelChoices =
                withContext(Dispatchers.IO) { wheelChoiceDAO.getAllWheelChoices().filter { it.text.isNotEmpty() } }
            if (wheelChoices.size > 1) {
                wheelView.buildWheel(wheelChoices)
            } else {
                wheelView.onNotEnoughWheelChoices()
            }
        }
    }

    //Get random amount of degrees to spin wheel, then calculate start and end point, then spin wheel based on this.
    override fun determineSpin() {
        val degreesToSpin = wheelUtils.determineDegreesToSpin()
        wheelEndingDegrees = wheelStartingDegrees + degreesToSpin

        wheelView.spinWheel(wheelStartingDegrees, wheelEndingDegrees)

        wheelStartingDegrees = wheelEndingDegrees
    }


    //Given final angle the wheel is rotated to, determine which choice is selected and send this info to WheelActivity.
    override fun determineChoiceWheelLandsOn(wheelPosition: Int) {

        val sections = wheelChoices.size
        val finalAngle: Int = wheelUtils.getFinalAngle(wheelPosition)
        val sectionLength = wheelUtils.getSectionLength(sections)
        val wheelSectionIndex = wheelUtils.getWheelSectionIndex(finalAngle, sectionLength)
        val choiceLandedOn = wheelChoices[wheelSectionIndex]

        CoroutineScope(Dispatchers.Main).launch {
            choiceLandedOn.timesLandedOn += 1
            withContext(Dispatchers.IO) { wheelChoiceDAO.update(choiceLandedOn)}
            wheelView.displayWinner(choiceLandedOn.text, choiceLandedOn.colorIndex)
        }
//        wheelView.displayWinner(choiceLandedOn.text, choiceLandedOn.colorIndex)
    }
}