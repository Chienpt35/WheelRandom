package xyz.sleekstats.wheelapp.db

import androidx.room.*
import xyz.sleekstats.wheelapp.model.WheelChoice

@Dao
interface WheelChoiceDAO {

    @Query("SELECT * from wheel_choice_table")
    fun getAllWheelChoices(): List<WheelChoice>

    @Query("SELECT * from wheel_choice_table WHERE id = :wheelChoiceID")
    fun getWheelChoice(wheelChoiceID: Int): WheelChoice

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(wheelChoice: WheelChoice): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(wheelChoices: List<WheelChoice>)

    @Update
    fun update(wheelChoice: WheelChoice)

    @Query("UPDATE wheel_choice_table SET text = :newText WHERE id = :id")
    fun updateText(newText: String, id: Long)


}