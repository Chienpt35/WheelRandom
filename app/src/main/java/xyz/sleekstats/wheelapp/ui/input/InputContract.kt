package xyz.sleekstats.wheelapp.ui.input

import xyz.sleekstats.wheelapp.db.WheelChoiceDatabase
import xyz.sleekstats.wheelapp.model.WheelChoice

interface InputContract {

    interface View {
        fun provideDatabase(): WheelChoiceDatabase
        fun goToWheelActivity()
    }

    interface Presenter {
        suspend fun getWheelChoices(): List<WheelChoice>
        fun insertWheelChoices(wheelChoices: List<WheelChoice>): Int
    }
}