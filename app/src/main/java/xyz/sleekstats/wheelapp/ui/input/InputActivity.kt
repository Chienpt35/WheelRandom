package xyz.sleekstats.wheelapp.ui.input

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_input.*
import kotlinx.android.synthetic.main.input_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.sleekstats.wheelapp.R
import xyz.sleekstats.wheelapp.db.WheelChoiceDatabase
import xyz.sleekstats.wheelapp.model.WheelChoice
import xyz.sleekstats.wheelapp.ui.wheel.WheelActivity

class InputActivity : AppCompatActivity(), InputContract.View {

    private lateinit var inputPresenter: InputPresenter

    private val colorList = listOf(
        Color.MAGENTA,
        Color.YELLOW,
        Color.GREEN,
        Color.RED,
        Color.BLUE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        inputPresenter = InputPresenter(this)

        val viewList = listOf(
            option1_input,
            option2_input,
            option3_input,
            option4_input,
            option5_input
        )
        for (i in 0 until viewList.size) {
            val view = viewList[i]
            view.input_edit_text.hint = "Option ${i + 1}"
            view.color_selector.setBackgroundColor(colorList[i])
        }

        CoroutineScope(Dispatchers.Main).launch {
            val wheelChoices = withContext(Dispatchers.IO) { inputPresenter.getWheelChoices() }

            /*wheelChoices.forEach {
                val i = it.id.toInt()
                val view = viewList[i]
                view.input_edit_text.setText(it.text)
            }*/

            save_choices_button.setOnClickListener {
                val choices = arrayListOf<WheelChoice>()
                for (i in 0 until 100) {
                    choices.add(
                        WheelChoice(i.toLong(), i.toString(), colorList.random())
                    )
                }
                insertAll(choices)
            }

        }
    }

    private fun insertAll(choices: List<WheelChoice>) =
        CoroutineScope(Dispatchers.Main).launch {
            var numberOfChoicesEntered = 0
            withContext(Dispatchers.IO) {
                numberOfChoicesEntered = inputPresenter.insertWheelChoices(choices)
            }
            if (numberOfChoicesEntered > 1) {
                goToWheelActivity()
            } else {
                Toast.makeText(this@InputActivity, getString(R.string.enter_more_choices_warning), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    override fun goToWheelActivity() {
        val newActivityIntent = Intent(this, WheelActivity::class.java)
        startActivity(newActivityIntent)
    }

    override fun provideDatabase(): WheelChoiceDatabase {
        return WheelChoiceDatabase.getDatabase(applicationContext)
    }
}

