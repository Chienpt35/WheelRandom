package xyz.sleekstats.wheelapp.ui.input

import xyz.sleekstats.wheelapp.model.WheelChoice

class InputPresenter(inputView: InputContract.View) :
    InputContract.Presenter {

    private val wheelChoiceDatabase = inputView.provideDatabase()
    private val wheelChoiceDAO = wheelChoiceDatabase.wheelChoiceDao()

    override fun insertWheelChoices(wheelChoices: List<WheelChoice>): Int {
        insertOrUpdateChoices(wheelChoices)
        return wheelChoices.filter { it.text.isNotEmpty() }.size
    }


    private fun insertOrUpdateChoices(wheelChoices: List<WheelChoice>) {
        wheelChoices.forEach {
            val i = wheelChoiceDAO.insert(it)
            if (i < 0) {
                wheelChoiceDAO.updateText(it.text, it.id)
            }
        }
    }

    override suspend fun getWheelChoices(): List<WheelChoice> {
        return wheelChoiceDAO.getAllWheelChoices()
    }
}