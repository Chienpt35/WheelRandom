package xyz.sleekstats.wheelapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xyz.sleekstats.wheelapp.model.WheelChoice

@Database(entities = [WheelChoice::class], version = 1)
abstract class WheelChoiceDatabase : RoomDatabase() {

    abstract fun wheelChoiceDao(): WheelChoiceDAO

    companion object {
        private var INSTANCE: WheelChoiceDatabase? = null

        fun getDatabase(context: Context): WheelChoiceDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WheelChoiceDatabase::class.java,
                    "wheel_choice_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
