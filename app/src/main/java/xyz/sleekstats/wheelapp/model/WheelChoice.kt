package xyz.sleekstats.wheelapp.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

/**Holds data for each user-entered wheel choice, including
 * @text - displays user's choice
 * @colorIndex - gives color code for choice to show when displayed (default is Color.BLACK)
 * @timesLandedOn - gives amount of times the choice has been landed on
 **/
@Entity(tableName = "wheel_choice_table")
data class WheelChoice(
    @PrimaryKey
    val id: Long,
    val text: String,
    val colorIndex: Int = Color.BLACK,
    var timesLandedOn: Int = 0
)