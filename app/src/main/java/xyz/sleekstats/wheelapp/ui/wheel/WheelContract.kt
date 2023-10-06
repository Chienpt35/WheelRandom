package xyz.sleekstats.wheelapp.ui.wheel

import xyz.sleekstats.wheelapp.db.WheelChoiceDatabase
import xyz.sleekstats.wheelapp.model.WheelChoice

interface WheelContract {

    interface View {
        fun buildWheel(wheelChoices : List<WheelChoice>)
        fun displayWinner(choiceText : String, colorCode: Int)
        fun spinWheel(wheelStartingDegrees: Float, wheelEndingDegrees : Float)
        fun provideDatabase() : WheelChoiceDatabase
        fun onNotEnoughWheelChoices()
    }

    interface Presenter {
        fun getWheelChoices()
        fun determineSpin()
        fun determineChoiceWheelLandsOn(wheelPosition: Int)
    }
}